package com.koleychik.core_authentication.impl

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kaleichyk.utils.CurrentUser
import com.kaleichyk.utils.getUserResultError
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AuthDbDataSource
import com.koleychik.core_authentication.api.AuthFirebaseDataSource
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.extencions.createUserFirstTime
import com.koleychik.core_authentication.extencions.loginUsingGoogle
import com.koleychik.core_authentication.extencions.toUser
import com.koleychik.core_authentication.result.GoogleSignInResult
import com.koleychik.models.asRoot
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.toUserResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.users.User
import com.koleychik.module_injector.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authFirebaseDataSource: AuthFirebaseDataSource,
    private val authDbDataSource: AuthDbDataSource
) : AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun createAccount(name: String, email: String, password: String): UserResult {
        val result = authFirebaseDataSource.createFirebaseUser(email, password)
        return when (result) {
            is CheckResult.Successful -> addUser(name, email)
            is CheckResult.Error -> result.toUserResult()
        }
    }

    override suspend fun login(email: String, password: String): UserResult {
        val result = authFirebaseDataSource.login(email, password)
        return when (result) {
            is CheckResult.Successful -> getUser()
            is CheckResult.Error -> result.toUserResult()
        }
    }

    override fun googleSingIn(activity: AppCompatActivity, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start googleSingIn")
        activity.loginUsingGoogle {
            when (it) {
                is GoogleSignInResult.Successful -> loginOrSingUsingGoogleAccount(it, res)
                is GoogleSignInResult.DataError -> res(getUserResultError(it.message))
                is GoogleSignInResult.ServerError -> res(UserResult.Error(it.message))
            }
        }
    }

    override suspend fun checkUser(): UserResult {
        val result = authFirebaseDataSource.checkUser()
        return when (result) {
            is CheckResult.Successful -> getUser()
            is CheckResult.Error -> return result.toUserResult()
        }
    }


    override suspend fun resetPassword(email: String): CheckResult {
        return authFirebaseDataSource.resetPassword(email)
    }

    private suspend fun getUser(): UserResult {
        Log.d(Constants.TAG, "AuthRepositoryImpl start getUser")
        val uid = auth.currentUser?.uid ?: return getUserResultError(R.string.cannot_find_user)
        val result = authDbDataSource.getUserByUid(uid)
        if (result is UserResult.Successful) bindUser(result)
        return result
    }


    private suspend fun addUser(name: String, email: String): UserResult {
        Log.d(Constants.TAG, "AuthRepositoryImpl start addUser")
        val user = getUser(name, email)

        return if (user == null) getUserResultError(R.string.cannot_create_user)
        else authDbDataSource.addUser(user)

    }

    private fun getUser(name: String, email: String): User? {
        Log.d(Constants.TAG, "AuthRepositoryImpl start getUser")
        val uid = auth.currentUser?.uid ?: return null
        val user = createUserFirstTime(uid, name, email)
        bindUser(user)
        return user
    }

    private fun loginOrSingUsingGoogleAccount(
        value: GoogleSignInResult.Successful,
        res: (UserResult) -> Unit
    ) {
        authFirebaseDataSource.loginFirebaseUserByCredential(value.credential) {
            val uid = auth.currentUser?.uid
            if (uid == null) res(getUserResultError(R.string.cannot_get_information_about_user))
            else {
                when (it) {
                    is CheckResult.Successful -> {
                        getOrPutUser(value.account.toUser(uid), res)
                    }
                    is CheckResult.Error -> res(it.toUserResult())
                }
            }
        }
    }

    private fun getOrPutUser(user: User, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start getOrPutUser")
        CoroutineScope(Dispatchers.IO).launch {
            var result = authDbDataSource.getUserByUid(user.id)

            if (result is UserResult.Successful) bindUser(result)
            else {
                result = authDbDataSource.addUser(user)
                if (result is UserResult.Successful) bindUser(result)
            }

            withContext(Dispatchers.Main) {
                res(result)
            }
        }
    }

    private fun bindUser(user: User) {
        CurrentUser.user = user.asRoot()
    }

    private fun bindUser(userResult: UserResult.Successful) {
        CurrentUser.user = userResult.user.asRoot()
    }

}