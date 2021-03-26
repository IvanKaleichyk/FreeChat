package com.koleychik.core_authorization

import com.koleychik.core_authorization.results.DataResult

fun checkPassword(password: String): DataResult {
    val passwordLength = password.length
    return when {
        5 > passwordLength -> DataResult.Error(R.string.password_is_too_short)
        passwordLength > 20 -> DataResult.Error(R.string.password_is_too_long)
        else -> DataResult.Successful
    }
}

fun checkEmail(email: String): DataResult {
    return if (email.contains('@') && email.contains('.')) DataResult.Successful
    else DataResult.Error(R.string.write_your_email)
}