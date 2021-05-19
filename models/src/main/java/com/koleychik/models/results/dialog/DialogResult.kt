package com.koleychik.models.results.dialog

import com.koleychik.models.Dialog
import com.koleychik.models.states.CheckDataState
import com.koleychik.models.states.DataState

sealed class DialogResult {
    class Successful(val dialog: Dialog) : DialogResult()
    class Error(val message: String) : DialogResult()
}

fun DialogResult.toDataState(): DataState {
    return when (this) {
        is DialogResult.Successful -> DataState.Result(dialog)
        is DialogResult.Error -> DataState.Error(message)
    }
}

fun DialogResult.toCheckDataState(): CheckDataState {
    return when (this) {
        is DialogResult.Successful -> CheckDataState.Successful
        is DialogResult.Error -> CheckDataState.Error(message)
    }
}