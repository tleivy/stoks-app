package com.example.stoks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stoks.databinding.ItemLayoutBinding

class ItemAdapter(val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: ItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Item) {
                // TODO: fix fields after matching item_layout.xml to addItemFragment
                binding.itemTitle.text = item.title
                binding.itemDescription.text = item.description
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])
}