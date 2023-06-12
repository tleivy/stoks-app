package com.example.stoks

//import android.app.Application

import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    //private var itemDao: ItemDao?

//    init {
//        val db  = ItemsDatabase.getDatabase(application)
//        itemDao = db.itemsDao()
//    }
    //@Provides
    fun getItems() = itemDao?.getItems()

    //@Provides
    suspend fun addItem(item: Item) {
        itemDao?.addItem(item)
    }

    //@Provides
    suspend fun deleteItem(item: Item) {
        itemDao?.deleteItem(item)
    }

    //@Provides
    suspend fun deleteAll() {
        itemDao?.deleteAll()
    }

    //@Provides
    suspend fun getTotalAmountForStock(string: String){
        itemDao?.getTotalAmountForStockFlow(string)
    }

}