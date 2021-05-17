package com.koleychik.core_authentication.api

import android.net.Uri
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.users.User

internal interface AuthDbDataSource {

    suspend fun addUser(user: User): UserResult
    suspend fun getUserByUid(uid: String): UserResult

    suspend fun updateUserEmail(email: String): CheckResult
    suspend fun updateName(name: String): CheckResult
    suspend fun updateIcon(uri: Uri): CheckResult
    suspend fun updateBackground(uri: Uri): CheckResult
    fun isUserOnline(isOnline: Boolean)

    fun subscribeToUserChanges()
    fun unSubscribeToUserChanges()
}