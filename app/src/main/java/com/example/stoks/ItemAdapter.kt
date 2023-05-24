package com.example.stoks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stoks.databinding.ItemLayoutBinding

class ItemAdapter(val items: List<Item> , private val callback: ItemListener) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    interface ItemListener{
        fun onItemClicked(index:Int)
        fun onItemLongClick(index:Int)

    }
    inner class ItemViewHolder(private val binding: ItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root),View.OnClickListener,View.OnLongClickListener {

        init{
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)

        }

        override fun onClick(p0: View?) {
           callback.onItemClicked(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            callback.onItemLongClick(adapterPosition)
            return false
        }

        fun bind(item: Item) {
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