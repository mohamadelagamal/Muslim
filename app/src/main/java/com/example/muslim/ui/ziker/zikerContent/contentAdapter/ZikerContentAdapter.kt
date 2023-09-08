package com.example.muslim.ui.ziker.zikerContent.contentAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.muslim.R
import com.example.muslim.databinding.ItemZekrContentBinding
import com.example.muslim.model.ziker.ZikerContent

class ZikerContentAdapter( var itemZikerList:List<ZikerContent>?=null):Adapter<ZikerContentAdapter.viewHolder>() {

    class viewHolder(val itemZekrContentBinding: ItemZekrContentBinding):ViewHolder(itemZekrContentBinding.root){
        fun bind(item: ZikerContent){
           itemZekrContentBinding.item=item
            itemZekrContentBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val viewBinding :ItemZekrContentBinding=DataBindingUtil
        .inflate(LayoutInflater.from(parent.context), R.layout.item_zekr_content,parent,false)
        return viewHolder(viewBinding)
    }

    override fun getItemCount(): Int =itemZikerList?.size?:0

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item= itemZikerList?.get(position)
        holder.bind(item!!)

    }
    fun changeData(newList: List<ZikerContent>?){
        itemZikerList=newList
        notifyDataSetChanged()
    }
}
