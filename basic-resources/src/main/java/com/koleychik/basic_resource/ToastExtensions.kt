package com.koleychik.basic_resource

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.showToast(textRes: Int) {
    Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()
}