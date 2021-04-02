package com.koleychik.dialogs

import android.app.Dialog
import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class InfoDialog(
    context: Context,
    private val titleRes: Int,
    private val textInfoRes: Int,
    private val onClickListenerToBtn: View.OnClickListener,
    private val inputType: Int = InputType.TYPE_CLASS_TEXT,
    private val isGoogleAuth: Boolean = false,
    private val onClickToGoogleAuth: View.OnClickListener? = null
) : Dialog(context) {

    private val edt: EditText by lazy {
        findViewById(R.id.edt)
    }

    private val textInfo: TextView by lazy {
        findViewById(R.id.text_info)
    }

    private val title: TextView by lazy {
        findViewById(R.id.title)
    }

    private val btn: Button by lazy {
        findViewById(R.id.btn)
    }

    private val btnGoogleAuth: ImageView by lazy {
        findViewById(R.id.googleAuth)
    }

    fun getText() = edt.text.toString()

    init {
        updateUI()
        setContentView(R.layout.info_dialog)
        btnGoogleAuth.run {
            isVisible = isGoogleAuth
            setOnClickListener(onClickToGoogleAuth)
        }

    }

    private fun updateUI() {
        title.setText(titleRes)
        textInfo.setText(textInfoRes)
        edt.run {
            setHint(textInfoRes)
            inputType = this@InfoDialog.inputType
        }
        btn.setOnClickListener(onClickListenerToBtn)
    }
}