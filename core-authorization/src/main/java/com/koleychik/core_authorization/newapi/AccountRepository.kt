package com.koleychik.core_authorization.newapi

import android.net.Uri
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.models.User

interface AccountRepository {

    var user: User?
    fun updateEmail(email: String, res: (UserResult) -> Unit)
    fun updatePassword(password: String, res: (UserResult) -> Unit)
    fun updateName(name: String, res: (UserResult) -> Unit)
    fun updateIcon(uri: Uri, res: (UserResult) -> Unit)
    fun updateBackground(uri: Uri, res: (UserResult) -> Unit)

}