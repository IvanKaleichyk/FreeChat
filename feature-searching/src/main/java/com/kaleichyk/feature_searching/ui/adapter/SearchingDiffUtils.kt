package com.kaleichyk.feature_searching.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.koleychik.models.users.User

class SearchingDiffUtils(private val oldList: List<User>, private val newList: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}