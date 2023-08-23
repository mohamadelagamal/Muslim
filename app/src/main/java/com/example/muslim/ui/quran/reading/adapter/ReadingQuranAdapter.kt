package com.example.muslim.ui.quran.reading.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim.R
import com.example.muslim.model.quran.SurahInfoItem


class ReadingQuranAdapter(
    var images: List<Int>? = null
) : RecyclerView.Adapter<ReadingQuranAdapter.ViewPagerViewholder>() {

    inner class ViewPagerViewholder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reading_quran,parent,false)
        return ViewPagerViewholder(view)
    }

    override fun getItemCount(): Int {
        return images?.size ?: 0
    }


    override fun onBindViewHolder(holder: ViewPagerViewholder, position: Int) {
        val currentImage = images?.get(position)

        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
        currentImage?.let { imageView.setImageResource(it) }

        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                // position this is number in onBindViewHolder and items[position] this number in list
                currentImage?.let { it1 -> onItemClickListener?.onItemClick(it1) }

            }


        }
    }

    var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}