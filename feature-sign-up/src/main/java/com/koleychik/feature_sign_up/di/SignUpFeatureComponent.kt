package com.koleychik.feature_sign_up.di

import com.koleychik.feature_sign_up.ui.screens.ForgotPasswordFragment
import com.koleychik.feature_sign_up.ui.screens.SignInFragment
import dagger.Component

@Component(modules = [], dependencies = [SignUpFeatureDependencies::class])
interface SignUpFeatureComponent : SignUpFeatureApi {

    fun inject(fragment: SignInFragment)
    fun inject(fragment: com.koleychik.feature_sing.ui.SignUpFragment)
    fun inject(fragment: ForgotPasswordFragment)

    companion object {
        fun initAndGet(dependenciesUp: SignUpFeatureDependencies) =
            DaggerSignFeatureComponent.builder().signFeatureDependencies(dependenciesUp).build()
    }

}