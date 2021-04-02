package com.koleychik.feature_start.di

import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.module_injector.injections.BaseDependencies

interface StartFeatureDependencies : BaseDependencies {

    fun authRepository(): AuthRepository
    fun navigation(): StartFeatureNavigation

}