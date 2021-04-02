package com.koleychik.core_authorization.newapi

import com.google.firebase.auth.AuthCredential
import com.koleychik.core_authorization.result.CheckResult

internal interface AuthFirebaseRepository {

    fun createFirebaseUser(email: String, password: String, res: (CheckResult) -> Unit)
    fun login(email: String, password: String, res: (CheckResult) -> Unit)
    fun loginFirebaseUserByCredential(credential: AuthCredential, res: (CheckResult) -> Unit)
    fun checkUser(res: (CheckResult) -> Unit)

}