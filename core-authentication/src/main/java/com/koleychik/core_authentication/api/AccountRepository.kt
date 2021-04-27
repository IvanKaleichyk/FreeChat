package com.koleychik.core_authentication.api

import android.net.Uri
import com.koleychik.models.results.CheckResult

interface AccountRepository {

    fun isEmailVerified(): Boolean
    fun setOnlineStatus(isOnline: Boolean)
    fun sendVerificationEmail(res: (CheckResult) -> Unit)
    fun updateEmail(email: String, res: (CheckResult) -> Unit)
    fun updatePassword(password: String, res: (CheckResult) -> Unit)
    fun updateName(name: String, res: (CheckResult) -> Unit)
    fun updateIcon(uri: Uri, res: (CheckResult) -> Unit)
    fun updateBackground(uri: Uri, res: (CheckResult) -> Unit)

    fun subscribeToUserChanges()
    fun unSubscribeToUserChanges()
}