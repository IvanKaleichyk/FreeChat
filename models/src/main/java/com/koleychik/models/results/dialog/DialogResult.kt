package com.koleychik.models.results.dialog

import com.koleychik.models.Dialog

sealed class DialogResult {
    class Successful(val dialog: Dialog) : DialogResult()
    abstract class Error : DialogResult()
    class DataError(val message: Int) : Error()
    class ServerError(val message: String) : Error()
}