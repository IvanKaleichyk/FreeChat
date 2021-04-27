package com.koleychik.models.results.user

import com.koleychik.models.users.User

sealed class UsersResult {
    class Successful(val list: List<User>) : UsersResult()
    class DataError(val message: Int) : UsersResult()
    class ServerError(val message: String) : UsersResult()
}