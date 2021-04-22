package com.kaleichyk.core_database.api

import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.results.user.UsersResult

interface UsersRepository {

    fun getUsers(startAt: Int, endAt: Int, res: (UsersResult) -> Unit)
    fun getUserById(id: String, res: (UserResult) -> Unit)
    fun searchByName(name: String, res: (UsersResult) -> Unit)
    fun deleteUser(id: String, res: (CheckResult) -> Unit)
}