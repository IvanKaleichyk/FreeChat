package com.koleychik.feature_all_dialogs.ui.adapter

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.kaleichyk.data.CurrentUser
import com.kaleichyk.data.NavigationConstants
import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_all_dialogs.R
import com.koleychik.feature_all_dialogs.databinding.ItemRvDialogBinding
import com.koleychik.models.Dialog
import javax.inject.Inject

internal class AllDialogsAdapter @Inject constructor(
    private val navigationApi: AllDialogFeatureNavigationApi
) : RecyclerView.Adapter<AllDialogsAdapter.MainViewHolder>() {

    var list = listOf<Dialog>()
        set(value) {
            val diffUtils = AllDialogsDiffUtils(list, value)
            val result = DiffUtil.calculateDiff(diffUtils)
            result.dispatchUpdatesTo(this)
            field = value
        }

    var start = 0
    private var startLoadNew = 40
    val period = 50

    lateinit var onLoadNewList: (start: Int, end: Long) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (position == startLoadNew) startLoading()
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    private fun startLoading() {
        startLoadNew = start + period - 10
        onLoadNewList(start, (start + period).toLong())
    }

    fun clearList() {
        list = listOf()
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: MainViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    inner class MainViewHolder(private val binding: ItemRvDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dialog: Dialog) {
            val interlocutor = getInterlocutor(dialog)
            with(binding) {
                loadIcon(imageView, interlocutor.icon)
                title.text = interlocutor.name
                message.text = dialog.lastMessage.text
                if (!dialog.lastMessage.isRead) card.setBackgroundResource(R.drawable.card_background_un_read_message)
            }
            createOnClickListener(dialog.id)
        }

        private fun loadIcon(view: ImageView, uri: Uri) {
            view.load(uri) {
                placeholder(R.drawable.account_icon_48)
            }
        }

        private fun createOnClickListener(dialogId: Long) {
            binding.card.setOnClickListener {
                navigationApi.fromDialogsFeatureGoToMessagesFeature(Bundle().apply {
                    putLong(NavigationConstants.DIALOG_ID, dialogId)
                })
            }
        }

        fun clear() {
            binding.imageView.clear()
        }

        private fun getInterlocutor(dialog: Dialog) =
            if (CurrentUser.user!!.id == dialog.user1.id) dialog.user1
            else dialog.user2
    }

}