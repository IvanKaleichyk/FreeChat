package com.kaleichyk.feature_select_image_impl.di

import com.kaleichyk.feature_select_image_api.SelectImageApi
import com.kaleichyk.feature_select_image_impl.SelectImageImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class SelectImageFeatureModule {

    @Binds
    abstract fun provideSelectImageApi(impl: SelectImageImpl): SelectImageApi

}