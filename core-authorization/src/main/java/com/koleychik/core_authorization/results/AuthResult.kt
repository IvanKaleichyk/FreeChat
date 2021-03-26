package com.koleychik.core_authorization.results

import com.koleychik.models.User

sealed class AuthResult {
    class Successful(val user: User) : AuthResult()
    class Error(val message: Int) : AuthResult()
}
