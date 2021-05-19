package com.koleychik.core_notifications

import com.koleychik.models.results.CheckResult

fun Exception.toCheckResultError(): CheckResult.Error {
    val message = localizedMessage ?: message.toString()
    return CheckResult.Error(message)
}