package com.koleychik.feature_loading_api

import android.view.View

interface LoadingApi {
    fun create(rootView: View)
    var isVisible: Boolean
    val layoutRes: Int
}