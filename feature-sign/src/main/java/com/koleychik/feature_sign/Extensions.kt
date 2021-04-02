package com.koleychik.feature_sign

import android.content.Context
import android.graphics.Paint
import android.widget.TextView
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.showToast(textRes: Int) {
    Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()
}

fun TextView.underlineText() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}