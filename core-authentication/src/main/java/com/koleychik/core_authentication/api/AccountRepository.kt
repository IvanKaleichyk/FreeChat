package com.koleychik.core_authentication.api

import android.net.Uri
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.User

interface AccountRepository {

    var user: User?
    fun isEmailVerified(): Boolean
    fun verifyEmail(res: (CheckResult) -> Unit)
    fun updateEmail(email: String, res: (UserResult) -> Unit)
    fun updatePassword(password: String, res: (UserResult) -> Unit)
    fun updateName(name: String, res: (UserResult) -> Unit)
    fun updateIcon(uri: Uri, res: (UserResult) -> Unit)
    fun updateBackground(uri: Uri, res: (UserResult) -> Unit)

}