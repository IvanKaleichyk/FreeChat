package com.koleychik.core_authorization.newapi

import android.app.Activity
import com.koleychik.core_authorization.result.user.UserResult

interface AuthRepository {

    fun createAccount(name: String, email: String, password: String, res: (UserResult) -> Unit)
    fun login(email: String, password: String, res: (UserResult) -> Unit)
    fun googleSingIn(activity: Activity, res: (UserResult) -> Unit)
    fun checkUser(res: (UserResult) -> Unit)

}