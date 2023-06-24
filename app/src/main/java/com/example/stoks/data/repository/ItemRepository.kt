package com.example.stoks.data.repository

import com.example.stoks.data.local.ItemDao
import com.example.stoks.data.model.Item
import javax.inject.Inject


class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    fun getItems() = itemDao.getItems()

    fun getFavorites() = itemDao.getFavorites()


    suspend fun addItem(item: Item) {
        itemDao?.addItem(item)
    }


    suspend fun deleteItem(item: Item) {
        itemDao?.deleteItem(item)
    }


    suspend fun deleteAll() {
        itemDao?.deleteAll()
    }

    suspend fun getTotalAmountForStock(string: String) {
        itemDao?.getTotalAmountForStockFlow(string)
    }

    suspend fun removeFromFavorites(item: Item) {
        item.isFavorite = false
        itemDao?.updateItem(item)
    }

    suspend fun addToFavorites(item: Item) {
        item.isFavorite = true
        itemDao?.updateItem(item)
    }

    suspend fun updateItem(item: Item) {
        itemDao?.updateItem(item)
    }

}