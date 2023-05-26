package com.example.stoks

import android.net.Uri

data class Item(
    val stockName: String, val stockSymbol: String, val stockPrice: Double,
    val stockAmount: Double, val stockImage: Uri?
)

// This is a singleton
object ItemManager {
    val items: MutableList<Item> = mutableListOf()

    fun add (item: Item) {
        items.add(item)
    }

    fun remove(index: Int) {
        items.removeAt(index)
    }



}