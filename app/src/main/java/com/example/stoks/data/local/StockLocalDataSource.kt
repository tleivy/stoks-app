package com.example.stoks.data.local

import com.example.stoks.data.model.StockLocalModel
import javax.inject.Inject

class StockLocalDataSource @Inject constructor(
    private val stockDao: StockDao,
) {
    fun getAllStocks() = stockDao.getAllStocks()

    fun getFavoriteStocks() = stockDao.getFavoriteStocks()

    suspend fun getStockByName(name: String): StockLocalModel? = stockDao.getStockByName(name)

    suspend fun getStockByTicker(ticker: String) = stockDao.getStockByTicker(ticker)

    suspend fun addNewStock(stock: StockLocalModel) = stockDao.addNewStock(stock)

    suspend fun deleteStock(stock: StockLocalModel) = stockDao.deleteStock(stock)

    suspend fun deleteAllStocks() = stockDao.deleteAllStocks()

    suspend fun removeFromFavorites(stock: StockLocalModel) {
        stock.isFavorite = false
        stockDao.updateStockData(stock)
    }

    suspend fun addToFavorites(stock: StockLocalModel) {
        stock.isFavorite = true
        stockDao.updateStockData(stock)
    }

    suspend fun updateStockData(stock: StockLocalModel) = stockDao.updateStockData(stock)

}