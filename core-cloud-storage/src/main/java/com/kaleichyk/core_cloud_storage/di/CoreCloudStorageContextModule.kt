package com.kaleichyk.core_cloud_storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreCloudStorageContextModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

}