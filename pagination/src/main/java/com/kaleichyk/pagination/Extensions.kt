package com.kaleichyk.pagination

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val TAG = "PAGINATION_TAG"

fun showLog(text: String) {
    Log.d(TAG, text)
}

fun ViewGroup.getView(res: Int): View = LayoutInflater.from(context).inflate(res, this, false)