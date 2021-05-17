package com.kaleichyk.core_cloud_storage.framework

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storageMetadata
import com.kaleichyk.core_cloud_storage.framework.results.DeleteResult
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class CloudStorageRepositoryImpl @Inject constructor() :
    CloudStorageRepository {

    private val storage = FirebaseStorage.getInstance()

    override suspend fun addImage(userId: String, uri: Uri, contentType: String?): UploadResult {
        val ref = storage.getReference(userId)
        val metadata = storageMetadata {
            this.contentType = contentType
        }

        return try {
            ref.putFile(uri, metadata).await()
            val downloadUrl = ref.downloadUrl.await()
            UploadResult.Successful(downloadUrl)
        } catch (e: StorageException) {
            e.toUploadResult()
        }
    }

    override suspend fun delete(path: String): DeleteResult {
        val ref = storage.getReference(path)

        return try {
            ref.delete().await()
            DeleteResult.Successful
        } catch (e: StorageException) {
            e.toDeleteResult()
        }
    }
}