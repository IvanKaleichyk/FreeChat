package com.koleychik.core_authorization

import com.koleychik.core_database.result.CheckResult

fun checkPassword(password: String): CheckResult {
    val passwordLength = password.length
    return when {
        5 > passwordLength -> CheckResult.Error(R.string.password_is_too_short)
        passwordLength > 20 -> CheckResult.Error(R.string.password_is_too_long)
        else -> CheckResult.Successful
    }
}

fun checkEmail(email: String): CheckResult {
    return if (email.contains('@') && email.contains('.')) CheckResult.Successful
    else CheckResult.Error(R.string.write_your_email)
}