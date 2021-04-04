package com.koleychik.core_authentication.di

import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.api.DataStoreRepository
import com.koleychik.module_injector.injections.BaseApi

interface AuthenticationCoreApi : BaseApi {
    fun accountRepository(): AccountRepository
    fun authRepository(): AuthRepository
    fun dataStoreRepository(): DataStoreRepository
}