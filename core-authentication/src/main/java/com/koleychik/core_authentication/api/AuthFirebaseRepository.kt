package com.koleychik.core_authentication.api

import com.google.firebase.auth.AuthCredential
import com.koleychik.models.results.CheckResult

internal interface AuthFirebaseRepository {

    fun createFirebaseUser(email: String, password: String, res: (CheckResult) -> Unit)
    fun login(email: String, password: String, res: (CheckResult) -> Unit)
    fun loginFirebaseUserByCredential(credential: AuthCredential, res: (CheckResult) -> Unit)
    fun checkUser(res: (CheckResult) -> Unit)
    fun resetPassword(email: String, res: (CheckResult) -> Unit)


}