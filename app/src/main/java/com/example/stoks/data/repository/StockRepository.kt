package com.example.stoks.data.repository

import com.example.stoks.data.local.StockDao
import com.example.stoks.data.model.Stock
import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.remote_db.StockRemoteDataSource
import com.example.stoks.data.utils.Resource
import javax.inject.Inject


class StockRepository @Inject constructor(
    private val stockDao: StockDao,
    private val remoteDataSource: StockRemoteDataSource
) {
    fun getItems() = stockDao.getAllStocks()

    fun getFavorites() = stockDao.getFavorites()

    suspend fun addItem(stock: Stock) = stockDao.addNewStock(stock)

    suspend fun deleteItem(stock: Stock) = stockDao.deleteStock(stock)

    suspend fun deleteAll() = stockDao.deleteAll()

    suspend fun removeFromFavorites(stock: Stock) {
        stock.isFavorite = false
        stockDao.updateStock(stock)
    }

    suspend fun addToFavorites(stock: Stock) {
        stock.isFavorite = true
        stockDao.updateStock(stock)
    }

    suspend fun updateItem(stock: Stock) = stockDao.updateStock(stock)

    suspend fun getRemoteStockData(stockTicker: String): Resource<StockRemoteModel> =
        remoteDataSource.getStockData(stockTicker)
}