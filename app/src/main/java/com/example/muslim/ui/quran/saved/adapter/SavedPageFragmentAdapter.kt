package com.example.muslim.ui.quran.saved.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim.R
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.databinding.ItemSavedBinding

class SavedPageFragmentAdapter(var items: List<SavedPage?>?) : RecyclerView.Adapter<SavedPageFragmentAdapter.ViewHolder>() {

    class ViewHolder(var itemBinding: ItemSavedBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: SavedPage) {
            itemBinding.itemSavedAdapter = item
            itemBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding: ItemSavedBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.item_saved, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        val pageNumber = pagesNumber()[position]
        item?.let { holder.bind(it) }
        holder.itemBinding.pageNumberBookMarkActivity.text = "page: ${item!!.pageNumber + 1}"
        holder.itemBinding.counterNumberTv.text = "${pageNumber}"
        // click item
        onItemClickInterface.let {
            holder.itemView.setOnClickListener { item.pageNumber.let { onItemClickInterface?.onItemClick(item.pageNumber) } }
        }
        onItemLongClick?.let {
            holder.itemView.setOnLongClickListener {
                items!![position]?.let { it1 -> onItemLongClick?.onItemClickLong(position, it1) }
                true
            }
            }

    }

    override fun getItemCount(): Int = items?.size ?: 0
    private fun pagesNumber() : ArrayList<Int>{
        val list = ArrayList<Int>()
        for(i in 1..items!!.size) {
            list.add(i)
        }
        return list
    }
    var onItemClickInterface:OnItemClickInterface?=null
    interface OnItemClickInterface{
        fun onItemClick(position: Int)
    }
    var onItemLongClick : setOnLongClickListener?=null
    interface setOnLongClickListener {
        fun onItemClickLong(pos: Int , item: SavedPage)
    }
    fun changData(savedPage: List<SavedPage>) {
        items = savedPage
        notifyDataSetChanged()
    }
}