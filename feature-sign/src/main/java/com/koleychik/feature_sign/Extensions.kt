package com.koleychik.feature_sign

import android.graphics.Paint
import android.widget.TextView

fun TextView.underlineText() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}