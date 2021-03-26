package com.koleychik.core_database.result.user

import com.koleychik.models.User

sealed class UserResult {
    class Successful(val user: User) : UserResult()
    class DataError(val message: Int) : UserResult()
    class ServiceError(val message: String): UserResult()
}
