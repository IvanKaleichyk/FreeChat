package com.koleychik.core_authorization.api

import com.koleychik.core_authorization.result.CheckResult

interface AccountRepository {
    fun updateEmail(email: String, res: (CheckResult) -> Unit)
    fun updatePassword(password: String, res: (CheckResult) -> Unit)
    fun updateBackground(url: String, res: (CheckResult) -> Unit)
    fun updateIcon(url: String, res: (CheckResult) -> Unit)
}