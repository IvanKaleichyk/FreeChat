package com.koleychik.models.results.dialog

import com.koleychik.models.Dialog
import com.koleychik.models.states.DataState

sealed class DialogsResult {
    class Successful(val list: List<Dialog>) : DialogsResult()
    class Error(val message: String) : DialogsResult()
}

fun DialogsResult.toDataState(): DataState {
    return when (this) {
        is DialogsResult.Successful -> DataState.Result(list)
        is DialogsResult.Error -> DataState.Error(message)
    }
}
