package com.example.stoks

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stocks.R
import com.example.stocks.databinding.ItemLayoutBinding


class ItemAdapter(val items: List<Item>, private val callback: ItemListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    interface ItemListener {
        fun onItemClicked(index: Int)
        fun onItemLongClick(index: Int)

    }

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            callback.onItemClicked(adapterPosition)
        }

        // TODO: remove?
        override fun onLongClick(p0: View?): Boolean {
            callback.onItemLongClick(adapterPosition)
            return false
        }

        fun bind(item: Item) {

            binding.stockPrice.text = item.currPrice.toString()
            binding.stockSymbol.text = item.stockSymbol
            //binding.stockAmount.text = item.stockAmount.toString()
            val priceDiff = item.currPrice - item.stockPrice
            val percentageChange = (priceDiff / item.stockPrice) * 100
            if (priceDiff < 0.0) {
                //Glide.with(binding.root).load(R.drawable.stock_down).centerCrop().into(binding.decreaseOrIncreaseImage)
               binding.decreaseOrIncreaseImage.setImageResource(R.drawable.stock_down)
            }
            // Display the percentage change
            if (priceDiff > 0) {
                binding.percent.text = "+%.1f%%".format(percentageChange)
            } else binding.percent.text = "%.1f%%".format(percentageChange)
            Glide.with(binding.root).load(item.stockImage).circleCrop().into(binding.itemImage)
        }
    }

    fun itemAt(position: Int)= items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )


        )

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(items[position])
}