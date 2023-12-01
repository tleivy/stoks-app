package com.example.stoks.data.repository

import com.example.stoks.data.local.ItemDao
import com.example.stoks.data.model.Stock
import javax.inject.Inject


class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    fun getItems() = itemDao.getItems()

    fun getFavorites() = itemDao.getFavorites()


    suspend fun addItem(item: Stock) {
        itemDao.addItem(item)
    }


    suspend fun deleteItem(item: Stock) {
        itemDao.deleteItem(item)
    }


    suspend fun deleteAll() {
        itemDao.deleteAll()
    }


    suspend fun removeFromFavorites(item: Stock) {
        item.isFavorite = false
        itemDao.updateItem(item)
    }

    suspend fun addToFavorites(item: Stock) {
        item.isFavorite = true
        itemDao.updateItem(item)
    }

    suspend fun updateItem(item: Stock) {
        itemDao.updateItem(item)
    }

}