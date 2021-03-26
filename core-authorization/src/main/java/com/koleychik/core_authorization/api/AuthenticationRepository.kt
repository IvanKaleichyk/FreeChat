package com.koleychik.core_authorization.api

import com.koleychik.core_authorization.result.CheckResult
import com.koleychik.core_authorization.result.user.UserResult

interface AuthenticationRepository {

    fun loginUsingGoogle()
    fun getUserFromFirebase(res: (UserResult) -> Unit)
    fun login(email: String, password: String, res: (CheckResult) -> Unit)
    fun createAccount(email: String, password: String, res: (CheckResult) -> Unit)

}