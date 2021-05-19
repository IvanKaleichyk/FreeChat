package com.koleychik.core_authentication

import com.kaleichyk.utils.getCheckResultError
import com.koleychik.models.results.CheckResult

fun checkName(name: String?): CheckResult {
    return if (name == null || name == "") getCheckResultError(R.string.indicate_name)
    else CheckResult.Successful
}

fun checkPassword(password: String?, repeatPassword: String?): CheckResult {
    if (password != repeatPassword) return getCheckResultError(R.string.password_mismatch)
    if (password == null || password.isEmpty()) return getCheckResultError(R.string.indicate_password)
    val passwordLength = password.length
    return when {
        6 > passwordLength -> getCheckResultError(R.string.password_is_too_short)
        passwordLength > 20 -> getCheckResultError(R.string.password_is_too_long)
        else -> CheckResult.Successful
    }
}

fun checkEmail(email: String?): CheckResult {
    if (email == null || email.isEmpty()) return getCheckResultError(R.string.indicate_email)
    return if (email.contains('@') && email.contains('.')) CheckResult.Successful
    else getCheckResultError(R.string.write_your_email)
}