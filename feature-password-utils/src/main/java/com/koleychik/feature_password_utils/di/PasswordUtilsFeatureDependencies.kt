package com.koleychik.feature_password_utils.di

import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_password_utils.SpecifyEmailNavigationApi
import com.koleychik.module_injector.injections.BaseDependencies

interface PasswordUtilsFeatureDependencies : BaseDependencies {
    fun loadingApi(): LoadingApi
    fun authRepository(): AuthRepository
    fun navigationApi(): SpecifyEmailNavigationApi
}