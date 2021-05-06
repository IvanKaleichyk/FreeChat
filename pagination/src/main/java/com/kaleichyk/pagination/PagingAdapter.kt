package com.kaleichyk.pagination

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList

abstract class PagingAdapter<ViewHolder : RecyclerView.ViewHolder, M, MResult>(
    private val sortedList: SortedList<M>,
    pagingSource: PagingSource<M, MResult>
) : RecyclerView.Adapter<ViewHolder>() {

    fun submitData(newList: List<M>) {
        sortedList.run {
            clear()
            addAll(newList)
        }
    }
}