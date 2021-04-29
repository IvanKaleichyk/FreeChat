package com.kaleichyk.core_cloud_storage.framework

import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import com.kaleichyk.core_cloud_storage.framework.results.DeleteResult
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult
import javax.inject.Inject

internal class CloudStorageRepositoryImpl @Inject constructor(): CloudStorageRepository {

    private val storage = FirebaseStorage.getInstance()

    override fun addImage(
        userId: String,
        uri: Uri,
        contentType: String?,
        res: (UploadResult) -> Unit
    ) {
        val ref = storage.getReference(userId)
        val metadata = storageMetadata {
            this.contentType = contentType
        }
        ref.putFile(uri, metadata).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                res(UploadResult.Successful(it))
            }.addOnFailureListener(onFailureListener(res))
        }.addOnFailureListener(onFailureListener(res))
    }

    override fun delete(path: String, res: (DeleteResult) -> Unit) {
        val ref = storage.getReference(path)
        ref.delete().addOnSuccessListener {
            res(DeleteResult.Successful)
        }.addOnFailureListener {exception ->
            if (exception.localizedMessage != null) res(DeleteResult.Error(exception.localizedMessage!!))
            else res(DeleteResult.Error(exception.message.toString()))
        }
    }

    private fun onFailureListener(res: (UploadResult) -> Unit) =
        OnFailureListener { exception ->
            if (exception.localizedMessage != null) res(UploadResult.Error(exception.localizedMessage!!))
            else res(UploadResult.Error(exception.message.toString()))
        }

}