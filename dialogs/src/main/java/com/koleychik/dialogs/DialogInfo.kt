package com.koleychik.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogInfo(
    private val listener: DialogInfoListener,
    private val title: String,
    private val message: String?,
    private val btnText: Int = R.string.ok
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context, R.style.AlertDialogTheme).setTitle(title).apply {
            if (message != null) setMessage(message)
        }.setPositiveButton(btnText) { dialog, _ ->
            listener.onClick(dialog)
        }.create()

    }
}