package com.example.muslim.ui.ziker.namesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.muslim.R
import com.example.muslim.databinding.ItemZekrNameBinding
import com.example.muslim.model.ziker.ZikerNames

class NamesAdapter(val list:List<ZikerNames>):Adapter<NamesAdapter.viewHolder>() {

    class viewHolder(val itemBinding: ItemZekrNameBinding) : ViewHolder(itemBinding.root) {
        fun bind(item: ZikerNames){
            itemBinding.item=item
            itemBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val viewBinding: ItemZekrNameBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.item_zekr_name, parent, false)
        return viewHolder(viewBinding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
                val item=list[position]
        holder.bind(item)
        holder.itemBinding.imageZekr.setImageResource(item.image!!)
    }
}