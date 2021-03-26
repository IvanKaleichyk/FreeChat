package com.koleychik.core_authorization.api

import com.koleychik.core_authorization.result.CheckResult
import com.koleychik.core_authorization.result.ListDbResult
import com.koleychik.core_authorization.result.SingleDbDataResult
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.core_authorization.result.user.UsersResult
import com.koleychik.models.User

interface FirebaseDatabaseRepository {

    fun putValue(path: String, value: Any, res: (CheckResult) -> Unit)
    fun <T> listValues(path: String, res: (ListDbResult) -> Unit)
    fun <T> getValue(path: String, res: (SingleDbDataResult) -> Unit)
    fun getUsers(res: (UsersResult) -> Unit)
    fun putUser(user: User, res: (CheckResult) -> Unit)
    fun getUserFromDb(uid: String, res: (UserResult) -> Unit)

}