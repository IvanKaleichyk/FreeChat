package com.koleychik.feature_sign.di

import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_sign.SignFeatureNavigation
import com.koleychik.module_injector.injections.BaseDependencies

interface SignFeatureDependencies : BaseDependencies {

    fun authRepository(): AuthRepository
    fun navigation(): SignFeatureNavigation
    fun loadingApi(): LoadingApi

}