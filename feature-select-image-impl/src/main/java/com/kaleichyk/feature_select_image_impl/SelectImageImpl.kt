package com.kaleichyk.feature_select_image_impl

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaleichyk.feature_select_image_api.SelectImageApi
import com.kaleichyk.feature_select_image_impl.adapter.SelectImageAdapter
import com.koleychik.basic_resource.showToast
import com.koleychik.models.DeviceImage
import javax.inject.Inject

internal class SelectImageImpl @Inject constructor() : SelectImageApi {

    private var rootView: View? = null

    private val adapter = SelectImageAdapter()

    private val btnClose by lazy {
        if (rootView == null) throw NullPointerException("SelectImageApi was not created")
        rootView!!.findViewById<View>(R.id.cardClose)
    }

    private val btnUpload by lazy {
        if (rootView == null) throw NullPointerException("SelectImageApi was not created")
        rootView!!.findViewById<View>(R.id.cardUpload)
    }

    private val rv by lazy {
        if (rootView == null) throw NullPointerException("SelectImageApi was not created")
        rootView!!.findViewById<RecyclerView>(R.id.rv)
    }
    override var list: List<DeviceImage> = listOf()
        set(value) {
            adapter.list = value
            field = value
        }

    override var onSelect: (DeviceImage) -> Unit = {}

    override var onClose: () -> Unit = {}

    override fun create(rootView: View) {
        this.rootView = rootView
        setupRv()
        btnUpload.setOnClickListener {
            if (adapter.selectedImage != null) onSelect(adapter.selectedImage!!)
            else it.context.applicationContext.showToast(R.string.please_select_image)
        }
        btnClose.setOnClickListener {
            onClose()
        }
    }

    override val layoutResource: Int = R.layout.layout_select_image

    private fun setupRv() {
        rv.run {
            layoutManager = GridLayoutManager(rootView.context.applicationContext, 3)
            adapter = this@SelectImageImpl.adapter
        }
    }

}