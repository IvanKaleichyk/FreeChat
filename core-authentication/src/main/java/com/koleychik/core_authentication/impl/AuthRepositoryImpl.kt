package com.koleychik.core_authentication.impl

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthDbRepository
import com.koleychik.core_authentication.api.AuthFirebaseRepository
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.extencions.createUserFirstTime
import com.koleychik.core_authentication.extencions.loginUsingGoogle
import com.koleychik.core_authentication.extencions.toUser
import com.koleychik.core_authentication.result.GoogleSignInResult
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.results.CheckResult
import com.koleychik.models.users.User
import com.koleychik.module_injector.Constants
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val authDbRepository: AuthDbRepository
) : AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun createAccount(
        name: String,
        email: String,
        password: String,
        res: (UserResult) -> Unit
    ) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start createAccount")
        authFirebaseRepository.createFirebaseUser(email, password) {
            when (it) {
                is CheckResult.Successful -> addUser(name, email, res)
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
            }
        }
    }

    override fun login(email: String, password: String, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start login")
        authFirebaseRepository.login(email, password) {
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
        authFirebaseRepository.checkUser {
            when (it) {
                is CheckResult.Successful -> getUser(res)
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
            }
        }
    }

    override fun resetPassword(email: String, res: (CheckResult) -> Unit) {
        authFirebaseRepository.resetPassword(email, res)
    }

    private fun getUser(res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start getUser")
        val uid = auth.currentUser?.uid ?: return
        authDbRepository.getUserByUid(uid) {
            if (it is UserResult.Successful) accountRepository.user = it.user
            res(it)
        }
    }

    private fun addUser(name: String, email: String, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthRepositoryImpl start addUser")
        val user = getUser(name, email)
        accountRepository.user = user
        if (user == null) res(UserResult.DataError(R.string.cannot_create_user))
        else {
            authDbRepository.addUser(user) { userResult ->
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
        authFirebaseRepository.loginFirebaseUserByCredential(value.credential) {
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
        authDbRepository.getUserByUid(user.id) { getUserResult ->
            if (getUserResult is UserResult.Successful) {
                accountRepository.user = user
                res(getUserResult)
            } else {
                authDbRepository.addUser(user) { addUserResult ->
                    accountRepository.user = user
                    res(addUserResult)
                }
            }
        }
    }

}