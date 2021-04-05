package com.koleychik.feature_password_utils.di

import com.koleychik.feature_password_utils.ui.SpecifyEmailFragment
import dagger.Component

@Component(
    modules = [ViewModelModule::class],
    dependencies = [PasswordUtilsFeatureDependencies::class]
)
interface PasswordUtilsFeatureComponent : PasswordUtilsFeatureApi {

    fun inject(fragment: SpecifyEmailFragment)

    companion object {
        fun initAndGet(dependencies: PasswordUtilsFeatureDependencies) =
            DaggerPasswordUtilsFeatureComponent
                .builder()
                .passwordUtilsFeatureDependencies(dependencies)
                .build()

    }

}