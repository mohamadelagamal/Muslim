package com.example.muslim.ui.hades.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim.R
import com.example.muslim.database.HadesData
import com.example.muslim.databinding.ItemHadesBinding

class HadesAdapter(var items:List<HadesData>?):RecyclerView.Adapter<HadesAdapter.HadesViewHolder>(){

    // make view holder for recycler view item
    class HadesViewHolder(var viewdataBinding:ItemHadesBinding) :
        RecyclerView.ViewHolder(viewdataBinding.root){ // root is the root view of item_hades.xml
            fun bind(hadesData: HadesData){
                viewdataBinding.itemHades=hadesData // set data binding for this item with hadesData
                viewdataBinding.invalidateAll() // refresh data binding for this item
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HadesViewHolder {
        val viewDataBinding:ItemHadesBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_hades,parent,false)
        return HadesViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: HadesViewHolder, position: Int) {
        var hadesList=items!![position]
        holder.bind(hadesList)


    }
}