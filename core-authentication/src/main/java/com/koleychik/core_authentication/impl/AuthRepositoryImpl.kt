package com.koleychik.core_authentication.impl

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kaleichyk.data.CurrentUser
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
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.users.User
import com.koleychik.models.users.UserRoot
import com.koleychik.module_injector.Constants
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authFirebaseDataSource: AuthFirebaseDataSource,
    private val authDbDataSource: AuthDbDataSource
) : AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun createAccount(
        name: String,
        email: String,
        password: String,
        res: (UserResult) -> Unit
    ) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start createAccount")
        authFirebaseDataSource.createFirebaseUser(email, password) {
            when (it) {
                is CheckResult.Successful -> addUser(name, email, res)
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
            }
        }
    }

    override fun login(email: String, password: String, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start login")
        authFirebaseDataSource.login(email, password) {
            when (it) {
                is CheckResult.Successful -> getUser(res)
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
            }
        }
    }

    override fun googleSingIn(activity: AppCompatActivity, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start googleSingIn")
        activity.loginUsingGoogle {
            when (it) {
                is GoogleSignInResult.Successful -> loginOrSingUsingGoogleAccount(it, res)
                is GoogleSignInResult.DataError -> res(UserResult.DataError(it.message))
                is GoogleSignInResult.ServerError -> res(UserResult.ServerError(it.message))
            }
        }
    }

    override fun checkUser(res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start checkUser")
        authFirebaseDataSource.checkUser {
            when (it) {
                is CheckResult.Successful -> getUser(res)
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
            }
        }
    }

    override fun resetPassword(email: String, res: (CheckResult) -> Unit) {
        authFirebaseDataSource.resetPassword(email, res)
    }

    private fun getUser(res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start getUser")
        val uid = auth.currentUser?.uid ?: return
        authDbDataSource.getUserByUid(uid) {
            if (it is UserResult.Successful) CurrentUser.user = it.user.asRoot()
            res(it)
        }
    }

    private fun addUser(name: String, email: String, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start addUser")
        val user = getUser(name, email)

        if (user == null) res(UserResult.DataError(R.string.cannot_create_user))
        else {
            CurrentUser.user = user.asRoot()
            authDbDataSource.addUser(user) { userResult ->
                res(userResult)
            }
        }
    }

    private fun getUser(name: String, email: String): User? {
        Log.d(Constants.TAG, "AuthRepositoryImpl start getUser")
        val uid = auth.currentUser?.uid ?: return null
        return createUserFirstTime(uid, name, email)
    }

    private fun loginOrSingUsingGoogleAccount(
        value: GoogleSignInResult.Successful,
        res: (UserResult) -> Unit
    ) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start loginOrSingUsingGoogleAccount")
        authFirebaseDataSource.loginFirebaseUserByCredential(value.credential) {
            val uid = auth.currentUser?.uid
            if (uid == null) res(UserResult.DataError(R.string.cannot_get_information_about_user))
            else {
                when (it) {
                    is CheckResult.Successful -> getOrPutUser(value.account.toUser(uid), res)
                    is CheckResult.DataError -> res(UserResult.DataError(it.message))
                    is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
                }
            }
        }
    }

    private fun getOrPutUser(user: User, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start getOrPutUser")
        authDbDataSource.getUserByUid(user.id) { getUserResult ->
            if (getUserResult is UserResult.Successful) {
                CurrentUser.user = user as UserRoot
                res(getUserResult)
            } else {
                authDbDataSource.addUser(user) { addUserResult ->
                    CurrentUser.user = user as UserRoot
                    res(addUserResult)
                }
            }
        }
    }

}