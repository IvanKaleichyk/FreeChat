package com.kaleichyk.core_cloud_storage.framework.results

import android.net.Uri

sealed class UploadResult {

    class Successful(val uri: Uri): UploadResult()
    class Error(val message: String): UploadResult()

}