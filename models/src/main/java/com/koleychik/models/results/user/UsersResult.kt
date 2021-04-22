package com.koleychik.models.results.user

import com.koleychik.models.users.User

sealed class UsersResult {
    class Successful(list: List<User>) : UsersResult()
    class DataError(message: Int) : UsersResult()
    class ServerError(message: String) : UsersResult()
}