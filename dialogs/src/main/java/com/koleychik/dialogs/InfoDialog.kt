package com.koleychik.dialogs

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView

class InfoDialog(context: Context, title: Int, text: Int, onBtnClick: (Dialog) -> Unit) :
    Dialog(context) {

    private val btn by lazy { findViewById<Button>(R.id.btn) }
    private val textInfo by lazy { findViewById<TextView>(R.id.text_info) }
    private val titleInfo by lazy { findViewById<TextView>(R.id.title) }

    fun setBtnTextRes(text: Int) {
        btn.setText(text)
    }

    fun setTextRes(text: Int) {
        textInfo.setText(text)
    }

    fun setTitleRes(text: Int) {
        titleInfo.setText(text)
    }

    init {
        setContentView(R.layout.info_dialog)

        titleInfo.setText(title)
        textInfo.setText(title)

        btn.setOnClickListener {
            onBtnClick(this)
        }
    }

}