package com.koleychik.core_authentication.api

import com.koleychik.models.results.user.UserResult
import com.koleychik.models.users.User

internal interface AuthDbRepository {

    fun addUser(user: User, res: (UserResult) -> Unit)
    fun getUserByUid(uid: String, res: (UserResult) -> Unit)
}