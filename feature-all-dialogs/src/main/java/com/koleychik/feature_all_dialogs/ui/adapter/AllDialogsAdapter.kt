package com.koleychik.feature_all_dialogs.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.kaleichyk.utils.navigation.NavigationConstants
import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_all_dialogs.R
import com.koleychik.feature_all_dialogs.databinding.ItemRvDialogBinding
import com.koleychik.models.dialog.Dialog
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

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
            with(binding) {
                dialog.receiver.icon?.let { loadIcon(imageView, it) }
                title.text = dialog.receiver.name
                dialog.lastMessage?.text?.let { lastMessage -> message.text = lastMessage }
                if (dialog.lastMessage?.isRead != true) card.setBackgroundResource(R.drawable.card_background_un_read_message)
            }
            createOnClickListener(dialog)
        }

        private fun loadIcon(view: ImageView, uri: String?) {
            view.load(uri) {
                placeholder(R.drawable.account_icon_48)
            }
        }

        private fun createOnClickListener(dialog: Dialog) {
            binding.card.setOnClickListener {
                navigationApi.fromDialogsFeatureGoToMessagesFeature(Bundle().apply {
                    putParcelable(NavigationConstants.DIALOG, dialog)
                })
            }
        }

        fun clear() {
            binding.imageView.clear()
        }
    }

}