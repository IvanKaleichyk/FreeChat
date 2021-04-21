package com.koleychik.core_authentication.api

import android.net.Uri
import com.koleychik.core_authentication.result.VerificationResult
import com.koleychik.models.results.CheckResult
import com.koleychik.models.users.User

interface AccountRepository {

    var user: User?
    fun isEmailVerified(): Boolean
    fun verifyEmail(res: (VerificationResult) -> Unit)
    fun updateEmail(email: String, res: (CheckResult) -> Unit)
    fun updatePassword(password: String, res: (CheckResult) -> Unit)
    fun updateName(name: String, res: (CheckResult) -> Unit)
    fun updateIcon(uri: Uri, res: (CheckResult) -> Unit)
    fun updateBackground(uri: Uri, res: (CheckResult) -> Unit)

}