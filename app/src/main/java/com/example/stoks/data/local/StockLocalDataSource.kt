package com.example.stoks.data.local

import com.example.stoks.data.model.Stock
import javax.inject.Inject

class StockLocalDataSource @Inject constructor(
    private val stockDao: StockDao,
) {
    fun getAllStocks() = stockDao.getAllStocks()

    fun getFavoriteStocks() = stockDao.getFavoriteStocks()

    suspend fun addNewStock(stock: Stock) = stockDao.addNewStock(stock)

    suspend fun deleteStock(stock: Stock) = stockDao.deleteStock(stock)

    suspend fun deleteAllStocks() = stockDao.deleteAllStocks()

    suspend fun removeFromFavorites(stock: Stock) {
        stock.isFavorite = false
        stockDao.updateStockData(stock)
    }

    suspend fun addToFavorites(stock: Stock) {
        stock.isFavorite = true
        stockDao.updateStockData(stock)
    }

    suspend fun updateStockData(stock: Stock) = stockDao.updateStockData(stock)

}