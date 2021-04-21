package com.koleychik.models.results

import com.koleychik.models.Dialog

sealed class DialogsResult {
    class Successful(list: List<Dialog>) : DialogsResult()
    class DataError(message: Int) : DialogsResult()
    class ServerError(message: String) : DialogsResult()
}
