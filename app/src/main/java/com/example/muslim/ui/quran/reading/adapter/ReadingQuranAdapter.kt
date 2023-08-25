package com.example.muslim.ui.quran.reading.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.example.muslim.R
import com.example.muslim.model.quran.SurahInfoItem
import com.example.muslim.ui.quran.reading.ReadingQuranActivity


class ReadingQuranAdapter(
    var images: List<Int>? = null,var itemCount :Int ?=  null
) : RecyclerView.Adapter<ReadingQuranAdapter.ViewPagerViewholder>() {


    inner class ViewPagerViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reading_quran, parent, false)
        return ViewPagerViewholder(view)
    }

    override fun getItemCount(): Int {
        return images?.size ?: 0
    }


    @SuppressLint("ResourceAsColor")
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
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                // position this is number in onBindViewHolder and items[position] this number in list
                currentImage?.let { it1 -> onItemClickListener?.onItemClick(it1) }

            }
        }


        val bookMarkList = holder.itemView.findViewById<LottieAnimationView>(R.id.markItem)
       if (position == itemCount) {
           Log.d("TAG", "onItemClick: $position")
           bookMarkList.visibility = View.VISIBLE
           bookMarkList.setMinAndMaxProgress(0.5f, 1.0f)
           bookMarkList.playAnimation()
           val filter = SimpleColorFilter(Color.parseColor("#FF0000"))
           val keyPath = KeyPath("**")
           val callback: LottieValueCallback<ColorFilter> = LottieValueCallback(filter)
           bookMarkList.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
       }



    }
    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}