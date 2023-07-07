package com.eulsapet.nogleproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eulsapet.nogleproject.databinding.ItemMarketListBinding
import com.eulsapet.nogleproject.repository.model.Data

class MarketListAdapter: ListAdapter<Data, MarketListAdapter.MarketListViewHolder>(ITEM_LIST) {

    companion object {
        val ITEM_LIST = object: DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem.sortId == newItem.sortId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMarketListBinding.inflate(layoutInflater, parent, false)

        return MarketListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MarketListViewHolder(private val binding: ItemMarketListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data) {
            binding.apply {
                items = data
                executePendingBindings()
            }
        }
    }
}