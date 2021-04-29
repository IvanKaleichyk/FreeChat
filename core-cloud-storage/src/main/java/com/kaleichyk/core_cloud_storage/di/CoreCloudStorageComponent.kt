package com.kaleichyk.core_cloud_storage.di

import dagger.Component

@Component(modules = [CoreCloudStorageModule::class])
interface CoreCloudStorageComponent : CoreCloudStorageApi {

    companion object {
        @Volatile
        private var component: CoreCloudStorageComponent? = null

        fun get(): CoreCloudStorageApi {
            if (component == null) synchronized(CoreCloudStorageComponent::class.java) {
                if (component == null) component = DaggerCoreCloudStorageComponent.builder().build()
            }
            return component!!
        }
    }

}