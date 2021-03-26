package com.koleychik.core_authorization.results

sealed class DataResult {
    object Successful : DataResult()
    class Error(val message: Int) : DataResult()
}