package com.koleychik.models.results.dialog

import com.koleychik.models.dialog.Dialog
import com.koleychik.models.states.CheckDataState
import com.koleychik.models.states.DataState

sealed class DialogResult {
    class Successful(val dialog: Dialog) : DialogResult()
    class Error(val message: String) : DialogResult()

    fun toDataState(): DataState {
        return when (this) {
            is Successful -> DataState.Result(dialog)
            is Error -> DataState.Error(message)
        }
    }

    fun toCheckDataState(): CheckDataState {
        return when (this) {
            is Successful -> CheckDataState.Successful
            is Error -> CheckDataState.Error(message)
        }
    }
}