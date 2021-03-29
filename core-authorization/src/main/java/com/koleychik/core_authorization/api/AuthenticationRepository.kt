package com.koleychik.core_authorization.api

import android.app.Activity
import com.koleychik.core_authorization.result.CheckResult
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.models.User

interface AuthenticationRepository {

    fun getUser(): User?
    fun Activity.loginUsingGoogle()
    fun checkUser(): String?
    fun loginUser(uid: String, res: (UserResult) -> Unit)
    fun getUserFromFirebase(res: (UserResult) -> Unit)
    fun login(email: String, password: String, res: (CheckResult) -> Unit)
    fun createAccount(email: String, password: String, res: (CheckResult) -> Unit)

}