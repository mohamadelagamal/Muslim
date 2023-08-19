package com.example.muslim.ui.quran.juzz.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim.R
import com.example.muslim.databinding.ItemsSurahBinding
import com.example.muslim.model.quran.SurahInfoItem
import java.security.AccessController.getContext


class SelectionQuranAdapter(val list:List<SurahInfoItem?>?=null,val mycontext: Context):RecyclerView.Adapter<SelectionQuranAdapter.ViewHolder>() {
    // view holder
    class ViewHolder(val itemJuzz:ItemsSurahBinding):RecyclerView.ViewHolder(itemJuzz.root){
        fun bind(item: SurahInfoItem?){
            itemJuzz.item=item
            itemJuzz.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewBinding:ItemsSurahBinding= DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.items_surah,parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = list?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= list?.get(position)
        holder.bind(item)

        holder.itemJuzz.totalVerse.text="from :${list?.get(position)?.count.toString().trimStart('0')}"
        holder.itemJuzz.place.text= "to :${list?.get(position)?.place}"
    }
}


