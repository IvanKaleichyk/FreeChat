package com.koleychik.core_authentication.result

sealed class CheckResult {
    object Successful : CheckResult()
    class DataError(val message: Int) : CheckResult()
    class ServerError(val message: String) : CheckResult()
}