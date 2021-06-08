package com.kaleichyk.feature_messages

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.kaleichyk.feature_messages.databinding.ItemRvErrorBinding
import com.kaleichyk.feature_messages.databinding.ItemRvMessageBinding
import com.kaleichyk.pagination.PaginationListener
import com.kaleichyk.pagination.PagingAdapter
import com.kaleichyk.pagination.PagingDiffUtil
import com.kaleichyk.pagination.getView
import com.kaleichyk.pagination.models.PaginationError
import com.kaleichyk.pagination.viewHolders.ErrorViewHolder
import com.kaleichyk.pagination.viewHolders.LoadingViewHolder
import com.kaleichyk.pagination.viewHolders.ShowDataViewHolder

class MessagesAdapter(
    paginationListener: PaginationListener,
    lifecycle: Lifecycle,
    onAddNewItem: (addToList: List<MessageData>) -> Unit
) : PagingAdapter<MessageData>(
    paginationListener,
    lifecycle,
    onAddNewItem,
    object : PagingDiffUtil<MessageData> {
        override fun compare(o1: MessageData, o2: MessageData): Int =
            (o1.createdAt - o2.createdAt).toInt()

        override fun areItemsTheSame(item1: MessageData, item2: MessageData): Boolean =
            item1.id == item2.id
    }
) {

    override fun createShowDataViewHolder(parent: ViewGroup): ShowDataViewHolder<MessageData> {
        val view = parent.getView(R.layout.item_rv_message)
        return MessageViewHolder(ItemRvMessageBinding.bind(view))
    }

    override fun createLoadingViewHolder(parent: ViewGroup) =
        LoadingViewHolder(parent.getView(R.layout.item_rv_loading))

    override fun createErrorViewHolder(parent: ViewGroup): ErrorViewHolder {
        val view = parent.getView(R.layout.item_rv_error)
        return ItemErrorViewHolder(ItemRvErrorBinding.bind(view))
    }

    class MessageViewHolder(private val binding: ItemRvMessageBinding) :
        ShowDataViewHolder<MessageData>(binding.root) {

        override fun bind(data: MessageData) {

        }
    }

    class ItemErrorViewHolder(private val binding: ItemRvErrorBinding) :
        ErrorViewHolder(binding.root) {

        override fun bind(error: PaginationError) {
            binding.textError.text = error.message
        }
    }
}