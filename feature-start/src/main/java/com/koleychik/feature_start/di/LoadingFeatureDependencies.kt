package com.koleychik.feature_start.di

import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.feature_start.LoadingNavigation
import com.koleychik.module_injector.injections.BaseDependencies

interface LoadingFeatureDependencies : BaseDependencies {

    fun authRepository(): AuthRepository
    fun navigation(): LoadingNavigation

}