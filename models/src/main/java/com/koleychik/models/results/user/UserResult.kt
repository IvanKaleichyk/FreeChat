package com.koleychik.models.results.user

import com.koleychik.models.users.User

sealed class UserResult {
    class Successful(val user: User) : UserResult()
    class DataError(val message: Int) : UserResult()
    object UserNotExists : UserResult()
    object UserNotInitialized : UserResult()
    object UnknownError : UserResult()
    class ServerError(val message: String) : UserResult()
}
