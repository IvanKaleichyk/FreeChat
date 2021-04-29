package com.kaleichyk.feature_select_image_impl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kaleichyk.feature_select_image_impl.R
import com.koleychik.models.DeviceImage

internal class SelectImageAdapter : RecyclerView.Adapter<SelectImageAdapter.MainViewHolder>() {

    var selectedImage: DeviceImage? = null
    private var selectedHolder: MainViewHolder? = null

    var list = listOf<DeviceImage>()
        set(value) {
            val diffUtil = SelectImageDiffUtils(field, value)
            val res = DiffUtil.calculateDiff(diffUtil)
            field = value
            res.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv_image, parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun onViewRecycled(holder: MainViewHolder) {
        super.onViewRecycled(holder)
        if (holder.isSelected) selectedHolder = null
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var isSelected = false

        private val image by lazy {
            itemView.findViewById<ImageView>(R.id.image)
        }

        val motionLayout by lazy {
            itemView.findViewById<MotionLayout>(R.id.motionLayout)
        }

        fun bind(model: DeviceImage) {
            if (selectedImage?.id == model.id) motionLayout.transitionToState(R.id.selected)
            image.load(model.uri)
            image.setOnClickListener {
                if (isSelected) motionLayout.transitionToState(R.id.not_selected)
                else motionLayout.transitionToState(R.id.selected)
                isSelected = !isSelected
                selectedHolder?.motionLayout?.transitionToState(R.id.not_selected)
                selectedHolder = this
                selectedImage = model
            }
        }
    }

}