package com.koleychik.basic_resource

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    Log.d("MAIN_APP_TAG", "showToast - $text")
}

fun Context.showToast(textRes: Int) {
    Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()
    Log.d("MAIN_APP_TAG", "showToast - ${getString(textRes)}")
}