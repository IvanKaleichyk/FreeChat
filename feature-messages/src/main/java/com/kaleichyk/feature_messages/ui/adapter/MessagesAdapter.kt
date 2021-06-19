package com.kaleichyk.feature_messages.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import coil.load
import com.kaleichyk.feature_messages.MessageData
import com.kaleichyk.feature_messages.R
import com.kaleichyk.pagination.getView
import com.kaleichyk.utils.CurrentUser

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MainViewHolder>() {

    companion object {
        const val ITEM_AUTHOR = 100
        const val ITEM_INTERLOCUTOR = 200
    }

    private var list = SortedList(
        MessageData::class.java,
        object : SortedListAdapterCallback<MessageData>(this) {
            override fun compare(o1: MessageData, o2: MessageData): Int {
                return (o2.createdAt - o1.createdAt).toInt()
            }

            override fun areContentsTheSame(
                oldItem: MessageData,
                newItem: MessageData
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                item1: MessageData,
                item2: MessageData
            ): Boolean = item1.id == item2.id
        })

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return when (item.authorId) {
            CurrentUser.user!!.id -> ITEM_AUTHOR
            else -> ITEM_INTERLOCUTOR
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return when (viewType) {
            ITEM_AUTHOR -> MainViewHolder(parent.getView(R.layout.item_rv_message_author))
            ITEM_INTERLOCUTOR -> MainViewHolder(parent.getView(R.layout.item_rv_message_receiver))
            else -> MainViewHolder(parent.getView(R.layout.item_rv_message_author))
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size()

    fun addItem(item: MessageData){
        list.add(item)
    }

    fun addToList(newList: List<MessageData>) {
        list.addAll(newList)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val messageText: TextView by lazy {
            itemView.findViewById(R.id.messageText)
        }

        private val messageImage: ImageView by lazy {
            itemView.findViewById(R.id.messageImage)
        }

        fun bind(data: MessageData) {
            messageText.text = data.text

            if (data.image == null) messageImage.visibility = View.GONE
            else messageImage.load(data.image)
        }
    }
}