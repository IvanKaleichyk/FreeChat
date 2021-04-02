package com.koleychik.feature_sign_up.di

import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.feature_sign_up.SignFeatureNavigation
import com.koleychik.module_injector.injections.BaseDependencies

interface SignUpFeatureDependencies : BaseDependencies {

    fun authRepository(): AuthRepository
    fun navigation(): SignFeatureNavigation

}