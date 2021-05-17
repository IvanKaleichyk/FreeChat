package com.koleychik.models.results.user

import com.koleychik.models.users.User

sealed class UsersResult {
    class Successful(val list: List<User>) : UsersResult()
    abstract class Error : UsersResult()
    class DataError(val message: Int) : Error()
    class ServerError(val message: String) : Error()
}