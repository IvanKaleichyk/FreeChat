package com.kaleichyk.feature_user_info.ui.dialogs

import android.content.Context
import com.kaleichyk.feature_user_info.R
import com.koleychik.dialogs.DialogGetData

class DialogSetName : DialogGetData(
    R.string.set_name,
    null,
    R.string.name,
    R.string.set,
    R.string.cancel,
) {

    private lateinit var dialogSetNameListener: DialogSetNameListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialogSetNameListener = requireParentFragment() as DialogSetNameListener

        onPositiveClick = { _, value ->
            dialogSetNameListener.setName(value)
        }
    }

    interface DialogSetNameListener {
        fun setName(value: String)
    }

}