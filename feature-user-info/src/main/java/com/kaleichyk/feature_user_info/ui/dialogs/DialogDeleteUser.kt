package com.kaleichyk.feature_user_info.ui.dialogs

import android.content.Context
import com.kaleichyk.feature_user_info.R
import com.koleychik.dialogs.DialogActionConfirmation

class DialogDeleteUser : DialogActionConfirmation(
    R.string.info,
    R.string.want_to_delete_account
) {

    private lateinit var listener: DialogDeleteUserListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = requireParentFragment() as DialogDeleteUserListener

        onPositiveClick = {
            listener.deleteUser()
        }
    }

    interface DialogDeleteUserListener {
        fun deleteUser()
    }

}