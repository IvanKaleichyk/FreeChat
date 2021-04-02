package com.koleychik.core_authorization

import com.koleychik.core_authorization.result.CheckResult

fun checkName(name: String?): CheckResult {
    return if (name == null || name == "") CheckResult.DataError(R.string.indicate_password)
    else CheckResult.Successful
}

fun checkPassword(password: String?, repeatPassword: String?): CheckResult {
    if (password != repeatPassword) return CheckResult.DataError(R.string.password_mismatch)
    if (password == null || password.isEmpty()) return CheckResult.DataError(R.string.indicate_password)
    val passwordLength = password.length
    return when {
        5 > passwordLength -> CheckResult.DataError(R.string.password_is_too_short)
        passwordLength > 20 -> CheckResult.DataError(R.string.password_is_too_long)
        else -> CheckResult.Successful
    }
}

fun checkEmail(email: String?): CheckResult {
    if (email == null || email.isEmpty()) return CheckResult.DataError(R.string.indicate_email)
    return if (email.contains('@') && email.contains('.')) CheckResult.Successful
    else CheckResult.DataError(R.string.write_your_email)
}