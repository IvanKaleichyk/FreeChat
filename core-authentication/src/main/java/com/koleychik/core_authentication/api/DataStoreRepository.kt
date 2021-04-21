package com.koleychik.core_authentication.api

import com.koleychik.models.users.User

interface DataStoreRepository {
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
}