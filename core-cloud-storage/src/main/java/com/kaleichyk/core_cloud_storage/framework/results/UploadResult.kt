package com.kaleichyk.core_cloud_storage.framework.results

import android.net.Uri
import com.koleychik.models.results.CheckResult

sealed class UploadResult {

    class Successful(val uri: Uri) : UploadResult()
    class Error(val message: String) : UploadResult()
}

fun UploadResult.toCheckResult(): CheckResult {
    return when (this) {
        is UploadResult.Successful -> CheckResult.Successful
        is UploadResult.Error -> CheckResult.Error(message)
    }
}
