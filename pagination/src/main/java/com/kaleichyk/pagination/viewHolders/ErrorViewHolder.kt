package com.kaleichyk.pagination.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kaleichyk.pagination.models.PaginationError

abstract class ErrorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(error: PaginationError)
}