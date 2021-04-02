package com.koleychik.feature_loading_impl

import android.view.View
import androidx.core.view.isVisible
import com.koleychik.feature_loading_api.LoadingApi

class LoadingImpl : LoadingApi {

    private var rootView: View? = null
    private val loadingRootView: View by lazy {
        checkRootView()
        rootView!!.findViewById(R.id.loadingRoot)
    }

    override fun create(rootView: View) {
        this.rootView = rootView
    }

    override var isVisible: Boolean = false
        set(value) {
            field = value
            loadingRootView.isVisible = field
        }

    override val layoutRes: Int
        get() = R.layout.layout_loading

    private fun checkRootView() {
        if (rootView == null) throw NullPointerException("first of all, you need call method create!!!")
    }

}