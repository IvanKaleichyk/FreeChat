package com.koleychik.feature_start.di

import com.koleychik.feature_start.ui.StartFragment
import dagger.Component

@Component(modules = [ViewModelModule::class], dependencies = [StartFeatureDependencies::class])
interface StartFeatureComponent : StartFeatureApi {

    fun inject(fragment: StartFragment)

    companion object {
        internal fun initAndGet(dependencies: StartFeatureDependencies) =
            DaggerStartFeatureComponent.builder().startFeatureDependencies(dependencies).build()
    }

}