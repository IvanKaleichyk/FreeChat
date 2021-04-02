package com.koleychik.feature_loading_impl.di

import com.koleychik.feature_loading_api.LoadingFeatureApi
import dagger.Component

@Component(modules = [LoadingFeatureModule::class])
interface LoadingFeatureComponent : LoadingFeatureApi {

    companion object {
        fun initAndGet() = DaggerLoadingFeatureComponent.builder().build()
    }

}