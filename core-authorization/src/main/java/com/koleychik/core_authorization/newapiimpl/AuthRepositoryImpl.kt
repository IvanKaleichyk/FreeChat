package com.koleychik.core_authorization.newapiimpl

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_authorization.R
import com.koleychik.core_authorization.extencions.createUserFirstTime
import com.koleychik.core_authorization.extencions.loginUsingGoogle
import com.koleychik.core_authorization.extencions.toUser
import com.koleychik.core_authorization.newapi.AccountRepository
import com.koleychik.core_authorization.newapi.AuthDbRepository
import com.koleychik.core_authorization.newapi.AuthFirebaseRepository
import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.core_authorization.result.CheckResult
import com.koleychik.core_authorization.result.GoogleSignInResult
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.models.User
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val authDbRepository: AuthDbRepository
) : AuthRepository {

    val auth = FirebaseAuth.getInstance()

    override fun createAccount(
        name: String,
        email: String,
        password: String,
        res: (UserResult) -> Unit
    ) {
        authFirebaseRepository.createFirebaseUser(email, password) {
            when (it) {
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
                is CheckResult.Successful -> addUser(name, email, res)
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
            }
        }
    }

    override fun login(email: String, password: String, res: (UserResult) -> Unit) {
        authFirebaseRepository.login(email, password) {
            when (it) {
                is CheckResult.Successful -> getUser(res)
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
            }
        }
    }

    override fun googleSingIn(activity: Activity, res: (UserResult) -> Unit) {
        activity.loginUsingGoogle {
            when (it) {
                is GoogleSignInResult.Successful -> loginOrSingUsingGoogleAccount(it, res)
                is GoogleSignInResult.DataError -> res(UserResult.DataError(it.message))
                is GoogleSignInResult.ServerError -> res(UserResult.ServerError(it.message))
            }
        }
    }

    override fun checkUser(res: (UserResult) -> Unit) {
        authFirebaseRepository.checkUser {
            when (it) {
                is CheckResult.Successful -> getUser(res)
                is CheckResult.DataError -> res(UserResult.DataError(it.message))
                is CheckResult.ServerError -> res(UserResult.ServerError(it.message))
            }
        }
    }

    private fun getUser(res: (UserResult) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        authDbRepository.getUserByUid(uid) {
            if (it is UserResult.Successful) accountRepository.user = it.user
            res(it)
        }
    }

    private fun addUser(name: String, email: String, res: (UserResult) -> Unit) {
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
        val uid = auth.currentUser?.uid ?: return null
        return createUserFirstTime(uid, name, email)
    }

    private fun loginOrSingUsingGoogleAccount(
        value: GoogleSignInResult.Successful,
        res: (UserResult) -> Unit
    ) {
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