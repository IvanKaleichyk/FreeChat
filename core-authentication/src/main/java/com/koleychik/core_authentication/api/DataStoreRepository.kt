package com.koleychik.core_authentication.api

import com.koleychik.models.User

interface DataStoreRepository {
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
}