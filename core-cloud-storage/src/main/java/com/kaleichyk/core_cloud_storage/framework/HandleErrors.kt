package com.kaleichyk.core_cloud_storage.framework

import com.google.firebase.storage.StorageException
import com.kaleichyk.core_cloud_storage.framework.results.DeleteResult
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult

fun StorageException.toUploadResult(): UploadResult.Error {
    val message = localizedMessage ?: message.toString()
    return UploadResult.Error(message)
}

fun StorageException.toDeleteResult(): DeleteResult.Error {
    val message = localizedMessage ?: message.toString()
    return DeleteResult.Error(message)
}