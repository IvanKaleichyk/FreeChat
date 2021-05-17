package com.koleychik.core_authentication.api

import androidx.appcompat.app.AppCompatActivity
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult

interface AuthRepository {
    suspend fun createAccount(name: String, email: String, password: String): UserResult
    suspend fun login(email: String, password: String): UserResult
    fun googleSingIn(activity: AppCompatActivity, res: (UserResult) -> Unit)
    suspend fun checkUser(): UserResult
    suspend fun resetPassword(email: String): CheckResult
}