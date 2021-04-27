package com.koleychik.models.results

import com.koleychik.models.Dialog

sealed class DialogsResult {
    class Successful(val list: List<Dialog>) : DialogsResult()
    class DataError(val message: Int) : DialogsResult()
    class ServerError(val message: String) : DialogsResult()
}
