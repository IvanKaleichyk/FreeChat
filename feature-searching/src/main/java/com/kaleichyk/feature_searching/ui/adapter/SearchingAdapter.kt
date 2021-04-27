package com.kaleichyk.feature_searching.ui.adapter

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kaleichyk.data.NavigationConstants.USER_ID
import com.kaleichyk.feature_searching.R
import com.kaleichyk.feature_searching.SearchingFeatureNavigationApi
import com.kaleichyk.feature_searching.databinding.SearchingItemRvBinding
import com.koleychik.models.users.User
import javax.inject.Inject

internal class SearchingAdapter @Inject constructor(
    private val navigationApi: SearchingFeatureNavigationApi
) : RecyclerView.Adapter<SearchingAdapter.MainViewHolder>() {

    var list = listOf<User>()
        set(value) {
            val diffUtils = SearchingDiffUtils(field, value)
            val result = DiffUtil.calculateDiff(diffUtils)
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        SearchingItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MainViewHolder(private val binding: SearchingItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: User) {
            with(binding) {
                model.icon?.let { uri -> loadIcon(imageView, uri) }
                title.text = model.name
                card.setOnClickListener { goToUserInfo(model.id) }
            }
        }

        private fun goToUserInfo(userId: String) {
            navigationApi.fromSearchingFeatureToUserInfoFeature(Bundle().apply {
                putString(USER_ID, userId)
            })
        }

        private fun loadIcon(view: ImageView, uri: Uri) {
            view.load(uri) {
                placeholder(R.drawable.account_icon_48)
            }
        }

    }
}