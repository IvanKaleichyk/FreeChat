package com.koleychik.core_authentication.api

import androidx.appcompat.app.AppCompatActivity
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.results.CheckResult

interface AuthRepository {

    fun createAccount(name: String, email: String, password: String, res: (UserResult) -> Unit)
    fun login(email: String, password: String, res: (UserResult) -> Unit)
    fun googleSingIn(activity: AppCompatActivity, res: (UserResult) -> Unit)
    fun checkUser(res: (UserResult) -> Unit)
    fun resetPassword(email: String, res: (CheckResult) -> Unit)

}