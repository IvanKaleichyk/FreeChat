package com.koleychik.models.results

import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.states.CheckDataState

sealed class CheckResult {
    object Successful : CheckResult()
    class Error(val message: String) : CheckResult()
}

fun CheckResult.toCheckDataState(): CheckDataState {
    return when (this) {
        is CheckResult.Successful -> CheckDataState.Successful
        is CheckResult.Error -> CheckDataState.Error(message)
    }
}

fun CheckResult.Error.toUserResult(): UserResult.Error = UserResult.Error(message)

fun CheckResult.Error.toDialogResult() = DialogResult.Error(message)