package com.kaleichyk.feature_user_info.ui.dialogs

import android.content.Context
import com.kaleichyk.feature_user_info.R
import com.koleychik.dialogs.DialogGetData

class DialogSetEmail : DialogGetData(
    R.string.set_email,
    null,
    R.string.email,
    R.string.set,
    R.string.cancel,
) {
    private lateinit var dialogSetEmailListener: DialogSetEmailListener
    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialogSetEmailListener = requireParentFragment() as DialogSetEmailListener

        onPositiveClick = { _, value ->
            dialogSetEmailListener.setEmail(value)
        }
    }

    interface DialogSetEmailListener {
        fun setEmail(value: String)
    }

}