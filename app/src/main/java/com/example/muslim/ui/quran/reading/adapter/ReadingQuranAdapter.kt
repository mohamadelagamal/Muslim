package com.example.muslim.ui.quran.reading.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.ColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.example.muslim.R
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.databinding.ItemReadingQuranBinding
import com.example.muslim.databinding.ItemZekrNameBinding
import com.example.muslim.ui.ziker.namesAdapter.NamesAdapter


class ReadingQuranAdapter(
    var images: List<Int>? = null, var listSavedPage: List<SavedPage>? = null
) : RecyclerView.Adapter<ReadingQuranAdapter.ViewHolder>() {


    class ViewHolder(val itemBinding: ItemReadingQuranBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: SavedPage) {
            itemBinding.item = item
            itemBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding: ItemReadingQuranBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_reading_quran,
                parent,
                false
            )
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return images?.size ?: 0
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImage = images?.get(position)
        holder.itemBinding.imageView.setImageResource(currentImage!!)
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                // position this is number in onBindViewHolder and items[position] this number in list
                currentImage?.let { it1 -> onItemClickListener?.onItemClick(it1) }

            }
        }

        listSavedPage?.forEach {
            if (it.pageNumber == position) {
                holder.itemBinding.markItem.visibility = View.VISIBLE
                holder.itemBinding.markItem.playAnimation()
                val colorFilter = SimpleColorFilter(Color.parseColor("#FF0000"))
                val keyPath = KeyPath("**")
                val callback = LottieValueCallback<ColorFilter>(colorFilter)
                holder.itemBinding.markItem.addValueCallback(
                    keyPath,
                    LottieProperty.COLOR_FILTER,
                    callback
                )
            }
        }

    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun changeListBookMark(list: List<SavedPage>){
        listSavedPage=list
        notifyDataSetChanged()
    }
}