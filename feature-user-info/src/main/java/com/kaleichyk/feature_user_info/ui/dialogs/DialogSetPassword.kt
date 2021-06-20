package com.kaleichyk.feature_user_info.ui.dialogs

import android.content.Context
import com.kaleichyk.feature_user_info.R
import com.koleychik.dialogs.DialogGetData

class DialogSetPassword : DialogGetData(
    R.string.set_password,
    null,
    R.string.password,
    R.string.set,
    R.string.cancel,
) {

    private lateinit var dialogSetPassword: DialogSetPasswordListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialogSetPassword = requireParentFragment() as DialogSetPasswordListener

        onPositiveClick = { _, value ->
            dialogSetPassword.setPassword(value)
        }
    }

    interface DialogSetPasswordListener {
        fun setPassword(value: String)
    }
}