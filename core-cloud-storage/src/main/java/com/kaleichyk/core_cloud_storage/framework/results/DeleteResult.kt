package com.kaleichyk.core_cloud_storage.framework.results

sealed class DeleteResult {

    object Successful : DeleteResult()
    class Error(message: String) : DeleteResult()

}
