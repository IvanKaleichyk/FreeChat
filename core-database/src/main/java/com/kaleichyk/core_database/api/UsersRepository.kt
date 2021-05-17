package com.kaleichyk.core_database.api

import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.results.user.UsersResult

interface UsersRepository {

    suspend fun getUsers(orderBy: String, startAfter: Int, limit: Long): UsersResult
    suspend fun getUserById(id: String): UserResult
    suspend fun searchByName(name: String): UsersResult
    suspend fun deleteUser(id: String): CheckResult
    suspend fun bindDialogIdToUser(userId: String, dialogId: Long): CheckResult
}