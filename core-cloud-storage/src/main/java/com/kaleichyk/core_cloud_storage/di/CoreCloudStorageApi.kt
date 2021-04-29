package com.kaleichyk.core_cloud_storage.di

import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository

interface CoreCloudStorageApi {

    fun cloudStorageRepository(): CloudStorageRepository

}