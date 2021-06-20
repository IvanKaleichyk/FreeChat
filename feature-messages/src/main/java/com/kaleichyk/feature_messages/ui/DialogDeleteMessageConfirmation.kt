package com.kaleichyk.feature_messages.ui

import android.content.Context
import com.kaleichyk.feature_messages.R
import com.koleychik.dialogs.DialogActionConfirmation

class DialogDeleteMessageConfirmation : DialogActionConfirmation(
    R.string.info,
    R.string.are_you_sure_you_want_to_delete_the_message
) {

    private lateinit var listener: DialogDeleteMessageConfirmationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = requireParentFragment() as DialogDeleteMessageConfirmationListener

        onPositiveClick = {
            listener.dialogDeleteMessageConfirmationSuccessful()
        }
    }

    interface DialogDeleteMessageConfirmationListener {
        fun dialogDeleteMessageConfirmationSuccessful()
    }

}