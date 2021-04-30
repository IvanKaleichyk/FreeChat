package com.koleychik.dialogs

import android.content.DialogInterface

interface DialogGetDataListener {
    fun onPositiveClick(dialog: DialogInterface, value: String)
}