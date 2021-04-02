package com.koleychik.core_authorization.newapi

import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.models.User

internal interface AuthDbRepository {

    fun addUser(user: User, res: (UserResult) -> Unit)
    fun getUserByUid(uid: String, res: (UserResult) -> Unit)

}