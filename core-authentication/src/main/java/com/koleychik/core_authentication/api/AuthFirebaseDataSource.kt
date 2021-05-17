package com.koleychik.core_authentication.api

import com.google.firebase.auth.AuthCredential
import com.koleychik.models.results.CheckResult

internal interface AuthFirebaseDataSource {

    suspend fun sendVerificationEmail(): CheckResult
    suspend fun createFirebaseUser(email: String, password: String): CheckResult
    suspend fun login(email: String, password: String): CheckResult
    fun loginFirebaseUserByCredential(credential: AuthCredential, res: (CheckResult) -> Unit)
    suspend fun checkUser(): CheckResult
    suspend fun resetPassword(email: String): CheckResult


}