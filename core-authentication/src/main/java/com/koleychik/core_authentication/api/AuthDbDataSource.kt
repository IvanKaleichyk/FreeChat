package com.koleychik.core_authentication.api

import android.net.Uri
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.users.User

internal interface AuthDbDataSource {

    fun addUser(user: User, res: (UserResult) -> Unit)
    fun getUserByUid(uid: String, res: (UserResult) -> Unit)

    fun updateUserEmail(email: String, res: (CheckResult) -> Unit)
    fun updateName(name: String, res: (CheckResult) -> Unit)
    fun updateIcon(uri: Uri, res: (CheckResult) -> Unit)
    fun updateBackground(uri: Uri, res: (CheckResult) -> Unit)
    fun isUserOnline(isOnline: Boolean)

    fun subscribeToUserChanges()
    fun unSubscribeToUserChanges()
}