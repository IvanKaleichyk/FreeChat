package com.koleychik.feature_start.di

import com.koleychik.feature_start.LoadingFragment
import dagger.Component

@Component(dependencies = [LoadingFeatureDependencies::class])
interface LoadingFeatureComponent : LoadingFeatureApi {

    fun inject(fragment: LoadingFragment)

    companion object {
        fun initAndGet(dependencies: LoadingFeatureDependencies) =
            DaggerLoadingFeatureComponent.builder().loadingFeatureDependencies(dependencies).build()
    }

}