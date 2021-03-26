package com.koleychik.core_database.result

sealed class CheckResult {
    object Successful : CheckResult()
    class DataError(val message: Int) : CheckResult()
    class ServerError(val message: String) : CheckResult()
}