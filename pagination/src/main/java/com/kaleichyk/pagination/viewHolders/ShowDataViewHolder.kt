package com.kaleichyk.pagination.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kaleichyk.pagination.models.PaginationData

abstract class ShowDataViewHolder<T : PaginationData>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: T)
}