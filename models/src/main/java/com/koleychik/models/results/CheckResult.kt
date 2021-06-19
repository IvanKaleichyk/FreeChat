package com.koleychik.models.results

import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.states.CheckDataState

sealed class CheckResult {
    object Successful : CheckResult()

    class Error(val message: String) : CheckResult() {
        fun toUserResult(): UserResult.Error = UserResult.Error(message)
        fun toDialogResult() = DialogResult.Error(message)
    }

    fun toCheckDataState(): CheckDataState {
        return when (this) {
            is Successful -> CheckDataState.Successful
            is Error -> CheckDataState.Error(message)
        }
    }
}