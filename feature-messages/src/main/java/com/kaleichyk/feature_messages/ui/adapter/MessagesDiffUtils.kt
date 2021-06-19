package com.kaleichyk.feature_messages.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kaleichyk.feature_messages.MessageData

class MessagesDiffUtils(
    private val oldList: List<MessageData>,
    private val newList: List<MessageData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}