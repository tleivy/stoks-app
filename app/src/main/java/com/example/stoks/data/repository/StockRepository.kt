package com.example.stoks.data.repository

import com.example.stoks.data.local.StockLocalDataSource
import com.example.stoks.data.model.StockLocalModel
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

    suspend fun getStockByName(name: String) = localDataSource.getStockByName(name)

    suspend fun getStockByTicker(ticker: String) = localDataSource.getStockByName(ticker)

    suspend fun addNewStock(stock: StockLocalModel) = localDataSource.addNewStock(stock)

    suspend fun deleteStock(stock: StockLocalModel) = localDataSource.deleteStock(stock)

    suspend fun deleteAllStocks() = localDataSource.deleteAllStocks()

    suspend fun updateStockData(stock: StockLocalModel) = localDataSource.updateStockData(stock)

    suspend fun addToFavorites(stock: StockLocalModel) {
        stock.isFavorite = true
        localDataSource.updateStockData(stock)
    }
    suspend fun removeFromFavorites(stock: StockLocalModel) {
        stock.isFavorite = false
        localDataSource.updateStockData(stock)
    }



    suspend fun getRemoteStockData(stockTicker: String): Resource<StockRemoteModel> =
        remoteDataSource.getStockData(stockTicker)
}