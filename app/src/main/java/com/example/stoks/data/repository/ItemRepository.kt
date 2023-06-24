package com.example.stoks.data.repository

//import android.app.Application

import com.example.stoks.data.local.ItemDao
import com.example.stoks.data.model.Item
import javax.inject.Inject


class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    //private var itemDao: ItemDao?

//    init {
//        val db  = ItemsDatabase.getDatabase(application)
//        itemDao = db.itemsDao()
//    }

    fun getItems() = itemDao?.getItems()

    fun getFavorites() = itemDao?.getFavorites()


    suspend fun addItem(item: Item) {
        itemDao?.addItem(item)
    }


    suspend fun deleteItem(item: Item) {
        itemDao?.deleteItem(item)
    }


    suspend fun deleteAll() {
        itemDao?.deleteAll()
    }

    suspend fun getTotalAmountForStock(string: String){
        itemDao?.getTotalAmountForStockFlow(string)
    }

    suspend fun removeFromFavorites(item: Item) {
        item.isFavorite = false
        itemDao.updateItem(item)
    }
    suspend fun addToFavorites(item: Item) {
        item.isFavorite = true
        itemDao.updateItem(item)
    }


}