package com.koleychik.core_authorization.result

sealed class SingleDbDataResult {
    class Successful<T>(value: T) : SingleDbDataResult()
    class DataError(message: Int) : SingleDbDataResult()
    class ServerError(message: String) : SingleDbDataResult()
}