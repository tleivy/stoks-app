package com.example.stoks.data.repository

import com.example.stoks.data.local.StockDao
import com.example.stoks.data.model.Stock
import javax.inject.Inject


class StockRepository @Inject constructor(private val stockDao: StockDao) {

    fun getItems() = stockDao.getItems()

    fun getFavorites() = stockDao.getFavorites()


    suspend fun addItem(stock: Stock) {
        stockDao.addItem(stock)
    }


    suspend fun deleteItem(stock: Stock) {
        stockDao.deleteItem(stock)
    }


    suspend fun deleteAll() {
        stockDao.deleteAll()
    }


    suspend fun removeFromFavorites(stock: Stock) {
        stock.isFavorite = false
        stockDao.updateItem(stock)
    }

    suspend fun addToFavorites(stock: Stock) {
        stock.isFavorite = true
        stockDao.updateItem(stock)
    }

    suspend fun updateItem(stock: Stock) {
        stockDao.updateItem(stock)
    }
}