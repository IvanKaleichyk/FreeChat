package com.koleychik.feature_start.di

import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.module_injector.injections.BaseDependencies

interface StartFeatureDependencies : BaseDependencies {

    fun authRepository(): AuthRepository
    fun accountRepository(): AccountRepository
    fun navigation(): StartFeatureNavigation
}