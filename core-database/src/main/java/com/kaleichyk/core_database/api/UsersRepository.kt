package com.kaleichyk.core_database.api

import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.results.user.UsersResult

interface UsersRepository {

    fun getUsers(orderBy: String, startAfter: Int, limit: Long, res: (UsersResult) -> Unit)
    fun getUserById(id: String, res: (UserResult) -> Unit)
    fun searchByName(name: String, res: (UsersResult) -> Unit)
    fun deleteUser(id: String, res: (CheckResult) -> Unit)
    fun bindDialogIdToUser(userId: String, dialogId : Long, res: (CheckResult) -> Unit)
}