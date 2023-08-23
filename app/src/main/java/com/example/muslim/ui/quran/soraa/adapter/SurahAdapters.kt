package com.example.muslim.ui.quran.soraa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.muslim.R
import com.example.muslim.databinding.ItemsSurahBinding
import com.example.muslim.model.quran.SurahInfoItem

class SurahAdapters(var list:List<SurahInfoItem?>?=null) :Adapter<SurahAdapters.viewholder>(){

    class viewholder(val itemsBinding: ItemsSurahBinding):ViewHolder(itemsBinding.root){
      fun bind(item:SurahInfoItem?){
          itemsBinding.item=item
          itemsBinding.invalidateAll()
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       val viewBinding:ItemsSurahBinding=DataBindingUtil
           .inflate(LayoutInflater.from(parent.context), R.layout.items_surah,parent,false)
        return viewholder(viewBinding)
    }

    override fun getItemCount(): Int =list?.size ?:0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewholder, position: Int) {
               val item=list!![position]
        holder.bind(item)
        holder.itemsBinding.totalVerse.text="Verses :${list!![position]?.count.toString().trimStart('0')}"
        holder.itemsBinding.place.text= "Place :${list!![position]?.place}"

        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                // position this is number in onBindViewHolder and items[position] this number in list
                item!!.index?.let { it1 -> onItemClickListener?.onItemClick(it1,item) }

            }


        }
    }
    fun changeData(newList:List<SurahInfoItem>){
       list=newList
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener?=null
    interface OnItemClickListener{
        fun onItemClick(item:String,surahInformationIem: SurahInfoItem)
    }
}