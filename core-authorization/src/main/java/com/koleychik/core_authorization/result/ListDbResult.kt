package com.koleychik.core_authorization.result

sealed class ListDbResult {
    class Successful<T>(list: List<T>) : ListDbResult()
    class DataError(message: Int) : ListDbResult()
    class ServerError(message: String) : ListDbResult()
}