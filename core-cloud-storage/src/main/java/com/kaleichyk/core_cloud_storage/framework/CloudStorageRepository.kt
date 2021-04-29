package com.kaleichyk.core_cloud_storage.framework

import android.net.Uri
import com.kaleichyk.core_cloud_storage.framework.results.DeleteResult
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult

interface CloudStorageRepository {

    fun addImage(userId: String, uri: Uri, contentType: String?, res: (UploadResult) -> Unit)
    fun delete(path: String, res: (DeleteResult) -> Unit)

}