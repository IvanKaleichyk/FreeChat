package com.kaleichyk.feature_select_image_api

import android.view.View
import com.koleychik.models.DeviceImage

interface SelectImageApi {

    var list: List<DeviceImage>

    var onSelect : (DeviceImage) -> Unit

    var onClose : () -> Unit

    fun create(rootView: View)

    val layoutResource: Int
}