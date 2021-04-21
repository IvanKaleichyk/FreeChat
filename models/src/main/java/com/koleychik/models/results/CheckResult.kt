package com.koleychik.models.results

sealed class CheckResult {
    object Successful : CheckResult()
    class DataError(val message: Int) : CheckResult()
    class ServerError(val message: String) : CheckResult()
}