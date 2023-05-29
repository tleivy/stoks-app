package com.example.stoks

import android.app.Application


class ItemRepository(application: Application) {

    private var itemDao: ItemDao?

    init {
        val db  = ItemsDatabase.getDatabase(application)
        itemDao = db.itemsDao()
    }

    fun getItems() = itemDao?.getItems()

    fun addItem(item: Item) {
        itemDao?.addItem(item)
    }

    fun deleteItem(item: Item) {
        itemDao?.deleteItem(item)
    }

    fun deleteAll() {
        itemDao?.deleteAll()
    }
}