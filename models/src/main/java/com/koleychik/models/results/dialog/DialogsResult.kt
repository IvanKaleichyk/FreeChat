package com.koleychik.models.results.dialog

import com.koleychik.models.dialog.Dialog
import com.koleychik.models.states.DataState

sealed class DialogsResult {
    class Successful(val list: List<Dialog>) : DialogsResult()
    class Error(val message: String) : DialogsResult()

    fun toDataState(): DataState {
        return when (this) {
            is Successful -> DataState.Result(list)
            is Error -> DataState.Error(message)
        }
    }
}