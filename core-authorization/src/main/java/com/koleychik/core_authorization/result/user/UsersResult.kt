package com.koleychik.core_authorization.result.user

import com.koleychik.models.User

sealed class UsersResult {
    class Successful(list: List<User>) : UsersResult()
    class DataError(message: Int) : UsersResult()
    class ServerError(message: String) : UsersResult()
}