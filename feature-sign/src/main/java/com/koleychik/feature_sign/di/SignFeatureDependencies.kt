package com.koleychik.feature_sign.di

import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_sign.navigation.SignInNavigationApi
import com.koleychik.feature_sign.navigation.SignUpNavigationApi
import com.koleychik.module_injector.injections.BaseDependencies

interface SignFeatureDependencies : BaseDependencies {

    fun authRepository(): AuthRepository
    fun navigationSignUp(): SignUpNavigationApi
    fun navigationSignIn(): SignInNavigationApi
    fun loadingApi(): LoadingApi

}