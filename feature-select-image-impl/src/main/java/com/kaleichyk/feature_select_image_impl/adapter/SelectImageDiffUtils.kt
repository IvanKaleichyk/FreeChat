package com.kaleichyk.feature_select_image_impl.adapter

import androidx.recyclerview.widget.DiffUtil
import com.koleychik.models.DeviceImage

internal class SelectImageDiffUtils(
    private val oldList: List<DeviceImage>,
    private val newList: List<DeviceImage>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}