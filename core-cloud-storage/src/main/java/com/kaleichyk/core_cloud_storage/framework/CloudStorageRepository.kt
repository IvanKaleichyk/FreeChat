package com.kaleichyk.core_cloud_storage.framework

import android.net.Uri
import com.kaleichyk.core_cloud_storage.framework.results.DeleteResult
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult

interface CloudStorageRepository {

    suspend fun addImage(userId: String, uri: Uri, contentType: String?): UploadResult
    suspend fun delete(path: String): DeleteResult

}