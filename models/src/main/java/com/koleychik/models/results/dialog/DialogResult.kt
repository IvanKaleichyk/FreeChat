package com.koleychik.models.results.dialog

import com.koleychik.models.Dialog

sealed class DialogResult {
        class Successful(val dialog: Dialog) : DialogResult()
        class DataError(val message: Int) : DialogResult()
        class ServerError(val message: String) : DialogResult()
}