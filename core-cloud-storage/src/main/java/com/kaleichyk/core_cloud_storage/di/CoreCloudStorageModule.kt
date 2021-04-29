package com.kaleichyk.core_cloud_storage.di

import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository
import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class CoreCloudStorageModule {

    @Binds
    @Singleton
    abstract fun provideCloudStorageRepository(impl: CloudStorageRepositoryImpl): CloudStorageRepository

}