package com.example.stoks.data.repository

import com.example.stoks.data.local.StockLocalDataSource
import com.example.stoks.data.model.Stock
import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.remote_db.StockRemoteDataSource
import com.example.stoks.data.utils.Resource
import javax.inject.Inject


class StockRepository @Inject constructor(
    private val localDataSource: StockLocalDataSource,
    private val remoteDataSource: StockRemoteDataSource
) {
    fun getAllStocks() = localDataSource.getAllStocks()

    fun getFavoriteStocks() = localDataSource.getFavoriteStocks()

    suspend fun addItem(stock: Stock) = localDataSource.addNewStock(stock)

    suspend fun deleteItem(stock: Stock) = localDataSource.deleteStock(stock)

    suspend fun deleteAllStocks() = localDataSource.deleteAllStocks()

    suspend fun removeFromFavorites(stock: Stock) {
        stock.isFavorite = false
        localDataSource.updateStockData(stock)
    }

    suspend fun addToFavorites(stock: Stock) {
        stock.isFavorite = true
        localDataSource.updateStockData(stock)
    }

    suspend fun updateItem(stock: Stock) = localDataSource.updateStockData(stock)

    suspend fun getRemoteStockData(stockTicker: String): Resource<StockRemoteModel> =
        remoteDataSource.getStockData(stockTicker)
}