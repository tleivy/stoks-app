package com.example.stoks

import android.app.Application


class ItemRepository(application: Application) {

    private var itemDao: ItemDao?

    init {
        val db  = ItemsDatabase.getDatabase(application)
        itemDao = db.itemsDao()
    }

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