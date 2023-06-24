package com.example.stoks.ui.allitems

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stoks.R
import com.example.stoks.databinding.ItemLayoutBinding
import com.example.stoks.data.model.Item


class ItemAdapter(var items: List<Item>, private val callback: ItemListener) :
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

        override fun onLongClick(p0: View?): Boolean {
            callback.onItemLongClick(adapterPosition)
            return false
        }

        fun bind(item: Item) {
            binding.stockName.text = item.stockName.toString()
            binding.stockPrice.text = "$%.2f".format(item.stockPrice)
            binding.stockSymbol.text = item.stockSymbol
            binding.amountBought.text = item.stockAmount.toString()
            binding.favoriteButton.isChecked = item.isFavorite
            Glide.with(binding.root).load(item.stockImage).circleCrop().into(binding.itemImage)

            binding.favoriteButton.setOnCheckedChangeListener { checkbox, isChecked ->
                if (isChecked) {
                    item.isFavorite = true
                    Toast.makeText(binding.root.context,
                        binding.root.context.getString(R.string.added_to_favorites),
                        Toast.LENGTH_SHORT).show()
                } else {
                    item.isFavorite = false
                    Toast.makeText(binding.root.context,
                        binding.root.context.getString(R.string.removed_from_favorites),
                        Toast.LENGTH_SHORT).show()
                }

            }

//            val priceDiff = item.currPrice - item.stockPrice
//            val percentageChange = (priceDiff / item.stockPrice) * 100
//            // Display the percentage change
//            if (priceDiff > 0) {
//                binding.percent.text = "+%.2f%%".format(percentageChange)
//                binding.percent.setTextColor(Color.rgb(79, 186, 111))
//            } else  {
//                binding.percent.text = "%.2f%%".format(percentageChange)
//                binding.percent.setTextColor(Color.RED)
//            }
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

    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
}