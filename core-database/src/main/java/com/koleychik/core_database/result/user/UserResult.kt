package com.koleychik.core_database.result.user

import com.koleychik.models.User

sealed class UserResult {
    class Successful(val user: User) : UserResult()
    class DataError(val message: Int) : UserResult()
    class ServerError(val message: String): UserResult()
}
