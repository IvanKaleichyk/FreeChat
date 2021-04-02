package com.koleychik.feature_sing.di

import com.koleychik.feature_sing.ui.screens.ForgotPasswordFragment
import com.koleychik.feature_sing.ui.screens.SignInFragment
import com.koleychik.feature_sing.ui.screens.SignUpFragment
import dagger.Component

@Component(modules = [], dependencies = [SignFeatureDependencies::class])
interface SignFeatureComponent : SignFeatureApi {

    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: ForgotPasswordFragment)

    companion object {
        fun initAndGet(dependenciesUp: SignFeatureDependencies) =
            DaggerSignFeatureComponent.builder().signFeatureDependencies(dependenciesUp).build()
    }

}