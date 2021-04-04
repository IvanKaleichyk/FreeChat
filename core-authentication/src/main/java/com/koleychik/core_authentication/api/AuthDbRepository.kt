package com.koleychik.core_authentication.api

import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.User

internal interface AuthDbRepository {

    fun addUser(user: User, res: (UserResult) -> Unit)
    fun getUserByUid(uid: String, res: (UserResult) -> Unit)
}