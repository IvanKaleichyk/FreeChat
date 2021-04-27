package com.koleychik.feature_all_dialogs.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.koleychik.models.Dialog

internal class AllDialogsDiffUtils(
    private val oldList: List<Dialog>,
    private val newList: List<Dialog>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}