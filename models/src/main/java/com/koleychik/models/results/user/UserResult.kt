package com.koleychik.models.results.user

import com.koleychik.models.users.User

sealed class UserResult {
    class Successful(val user: User) : UserResult()
    abstract class Error : UserResult()
    class DataError(val message: Int) : Error()
    class ServerError(val message: String) : Error()
}
