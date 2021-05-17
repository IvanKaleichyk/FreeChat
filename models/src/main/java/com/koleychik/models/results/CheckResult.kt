package com.koleychik.models.results

import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.user.UserResult

sealed class CheckResult {
    object Successful : CheckResult()
    abstract class Error : CheckResult()
    class DataError(val message: Int) : Error()
    class ServerError(val message: String) : Error()
}

fun CheckResult.Error.toUserResult(): UserResult.Error {
    return when (this) {
        is CheckResult.DataError -> UserResult.DataError(message)
        is CheckResult.ServerError -> UserResult.ServerError(message)
        else -> UserResult.ServerError("Error")
    }
}

fun CheckResult.toDialogResult(): DialogResult.Error {
    return when (this) {
        is CheckResult.DataError -> DialogResult.DataError(message)
        is CheckResult.ServerError -> DialogResult.ServerError(message)
        else -> DialogResult.ServerError("Error")
    }
}