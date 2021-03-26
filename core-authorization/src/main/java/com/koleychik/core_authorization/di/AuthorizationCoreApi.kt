package com.koleychik.core_authorization.di

import com.koleychik.core_authorization.api.AccountRepository
import com.koleychik.core_authorization.api.AuthenticationRepository
import com.koleychik.core_authorization.api.FirebaseDatabaseRepository
import com.koleychik.module_injector.injections.BaseApi

interface AuthorizationCoreApi : BaseApi {
    fun accountRepository(): AccountRepository
    fun authenticationRepository(): AuthenticationRepository
    fun firebaseDatabaseRepository(): FirebaseDatabaseRepository
}