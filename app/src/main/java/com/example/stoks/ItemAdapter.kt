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
                  binding.stockName.text = item.stockName
                  binding.stockPrice.text = item.stockPrice.toString()
                binding.stockSymbol.text = item.stockSymbol
                binding.stockAmount.text = item.stockAmount.toString()

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])
}