package com.koleychik.feature_sing.di

import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.feature_sing.SignFeatureNavigation
import com.koleychik.module_injector.injections.BaseDependencies

interface SignFeatureDependencies : BaseDependencies {

    fun authRepository(): AuthRepository
    fun navigation(): SignFeatureNavigation

}