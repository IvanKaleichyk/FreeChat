package com.koleychik.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

abstract class DialogInfo(
    private val title: Int,
    private val message: Int?,
    private val btnText: Int = R.string.ok
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context, R.style.AlertDialogTheme).setTitle(title).apply {
            if (message != null) setMessage(message)
        }.setPositiveButton(btnText) { _, _ ->
            dismiss()
        }.create()
    }
}