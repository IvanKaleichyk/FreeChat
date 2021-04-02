package com.koleychik.feature_loading_impl.di

import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_loading_impl.LoadingImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LoadingFeatureModule {

    @Binds
    abstract fun provideLoadingApi(impl: LoadingImpl): LoadingApi

}