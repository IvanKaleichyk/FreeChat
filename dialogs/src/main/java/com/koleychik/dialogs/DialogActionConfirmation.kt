package com.koleychik.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment

abstract class DialogActionConfirmation(
    private val title: Int,
    private val textInfoRes: Int,
) : DialogFragment() {


    private lateinit var rootView: View

    private lateinit var textInfo: TextView

    lateinit var onPositiveClick: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        rootView = View.inflate(context, R.layout.dialog_action_confirmation, null)
        textInfo = rootView.findViewById(R.id.text_info)
        textInfo.setText(textInfoRes)
        return AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setTitle(title)
            .setView(rootView)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                onPositiveClick()
            }
            .setNeutralButton(R.string.no) { _, _ ->
                dismiss()
            }
            .create()
    }
}