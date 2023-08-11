package com.example.muslim.ui.quran.surah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.muslim.R
import com.example.muslim.databinding.SurahItemsBinding
import com.example.muslim.model.SurahInfoItem

class SurahAdapters(var list:List<SurahInfoItem?>?=null) :Adapter<SurahAdapters.viewholder>(){

    class viewholder(val itemsBinding: SurahItemsBinding):ViewHolder(itemsBinding.root){
      fun bind(item:SurahInfoItem?){
          itemsBinding.item=item
          itemsBinding.invalidateAll()
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       val viewBinding:SurahItemsBinding=DataBindingUtil
           .inflate(LayoutInflater.from(parent.context), R.layout.surah_items,parent,false)
        return viewholder(viewBinding)
    }

    override fun getItemCount(): Int =list?.size ?:0

    override fun onBindViewHolder(holder: viewholder, position: Int) {
               val item=list!![position]
        holder.bind(item)
        holder.itemsBinding.totalVerse.text="Verses :${list!![position]?.count.toString().trimStart('0')}"
        holder.itemsBinding.place.text= "Place :${list!![position]?.place}"
    }
    fun changeData(newList:List<SurahInfoItem>){
       list=newList
        notifyDataSetChanged()
    }
}