package com.koleychik.core_authorization

import com.koleychik.core_authorization.result.CheckResult

fun checkPassword(password: String): CheckResult {
    val passwordLength = password.length
    return when {
        5 > passwordLength -> CheckResult.DataError(R.string.password_is_too_short)
        passwordLength > 20 -> CheckResult.DataError(R.string.password_is_too_long)
        else -> CheckResult.Successful
    }
}

fun checkEmail(email: String): CheckResult {
    return if (email.contains('@') && email.contains('.')) CheckResult.Successful
    else CheckResult.DataError(R.string.write_your_email)
}