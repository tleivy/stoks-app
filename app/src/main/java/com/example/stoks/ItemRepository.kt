package com.example.stoks

//import android.app.Application

import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton


class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    //private var itemDao: ItemDao?

//    init {
//        val db  = ItemsDatabase.getDatabase(application)
//        itemDao = db.itemsDao()
//    }

    fun getItems() = itemDao?.getItems()


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

}