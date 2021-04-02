package com.koleychik.feature_start.di

import com.koleychik.feature_start.StartFragment
import dagger.Component

@Component(dependencies = [StartFeatureDependencies::class])
interface StartFeatureComponent : StartFeatureApi {

    fun inject(fragment: StartFragment)

    companion object {
        fun initAndGet(dependencies: StartFeatureDependencies) =
            DaggerLoadingFeatureComponent.builder().loadingFeatureDependencies(dependencies).build()
    }

}