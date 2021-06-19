package com.koleychik.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class DialogGetData(
    private val title: Int,
    private val message: Int?,
    private val hint: Int,
    private val listener: DialogGetDataListener,
    private val positiveBtnText: Int,
    private val neutralBtnText: Int,
    private val inputType: Int = InputType.TYPE_CLASS_TEXT,
) : DialogFragment() {

    private lateinit var edt: EditText

    private lateinit var rootView: View


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        rootView = View.inflate(context,R.layout.get_data_dialog,null)
        edt = rootView.findViewById(R.id.edt)
        return AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setTitle(title)
            .apply {
                if (message != null) setMessage(message)
            }
            .setView(rootView)
            .setPositiveButton(positiveBtnText){ dialog, _ ->
                listener.onPositiveClick(dialog, edt.text.toString().trim())
            }
            .setNeutralButton(neutralBtnText){ _, _ ->
            }
            .create()
    }

    override fun onStart() {
        super.onStart()
        edt.setHint(hint)
        edt.inputType = inputType
    }


}