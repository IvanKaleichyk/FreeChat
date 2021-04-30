package com.kaleichyk.core_cloud_storage.di

import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Component(modules = [CoreCloudStorageModule::class, CoreCloudStorageContextModule::class])
@Singleton
interface CloudStorageCoreComponent : CoreCloudStorageApi {

    companion object {
        @Volatile
        private var component: CloudStorageCoreComponent? = null

        fun get(context: Context): CoreCloudStorageApi {
            if (component == null) synchronized(CloudStorageCoreComponent::class.java) {
                if (component == null) component = DaggerCloudStorageCoreComponent
                    .builder()
                    .coreCloudStorageContextModule(CoreCloudStorageContextModule(context))
                    .build()
            }
            return component!!
        }
    }

}