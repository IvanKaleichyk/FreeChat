package com.koleychik.core_authentication.result.user

import com.koleychik.models.User

sealed class UserResult {
    class Successful(val user: User) : UserResult()
    class DataError(val message: Int) : UserResult()
    object UserNotExists : UserResult()
    object UserNotInitialized : UserResult()
    object UnknownError : UserResult()
    class ServerError(val message: String) : UserResult()
}
