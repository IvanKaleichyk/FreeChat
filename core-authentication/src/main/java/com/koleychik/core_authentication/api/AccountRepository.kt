package com.koleychik.core_authentication.api

import android.net.Uri
import com.koleychik.models.results.CheckResult

interface AccountRepository {

    fun isEmailVerified(): Boolean
    fun setOnlineStatus(isOnline: Boolean)
    suspend fun sendVerificationEmail(): CheckResult
    suspend fun updateEmail(email: String): CheckResult
    suspend fun updatePassword(password: String): CheckResult
    suspend fun updateName(name: String): CheckResult
    suspend fun updateIcon(uri: Uri): CheckResult
    suspend fun updateBackground(uri: Uri): CheckResult
    fun signOut()
    suspend fun deleteUser(): CheckResult

    fun subscribeToUserChanges()
    fun unSubscribeToUserChanges()
}