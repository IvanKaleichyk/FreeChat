package com.koleychik.core_authorization.di

import com.koleychik.core_authorization.newapi.AccountRepository
import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.module_injector.injections.BaseApi

interface AuthorizationCoreApi : BaseApi {
    fun accountRepository(): AccountRepository
    fun authRepository(): AuthRepository
}