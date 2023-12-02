package com.example.stoks.data.repository

import android.util.Log
import com.example.stoks.data.local.StockLocalDataSource
import com.example.stoks.data.model.StockLocalModel
import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.remote_db.StockRemoteDataSource
import com.example.stoks.data.utils.Loading
import com.example.stoks.data.utils.Resource
import com.example.stoks.data.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StockRepository @Inject constructor(
    private val localDataSource: StockLocalDataSource,
    private val remoteDataSource: StockRemoteDataSource
) {
    fun getAllStocks() = localDataSource.getAllStocks()

    fun getFavoriteStocks() = localDataSource.getFavoriteStocks()

    suspend fun getStockByName(name: String) {
        withContext(Dispatchers.IO) {
            localDataSource.getStockByName(name)
        }
    }

    suspend fun getStockByTicker(ticker: String): StockLocalModel {
        withContext(Dispatchers.IO) {
            localDataSource.getStockByTicker(ticker)
        }
    }

    suspend fun addNewStock(stock: StockLocalModel) {
        withContext(Dispatchers.IO) {
            localDataSource.addNewStock(stock)
        }
    }

    suspend fun deleteStock(stock: StockLocalModel) {
        withContext(Dispatchers.IO) {
            localDataSource.deleteStock(stock)
        }
    }

    suspend fun deleteAllStocks() {
        withContext(Dispatchers.IO) {
            localDataSource.deleteAllStocks()
        }
    }

    suspend fun updateStockData(stock: StockLocalModel) {
        withContext(Dispatchers.IO) {
            localDataSource.updateStockData(stock)
        }
    }

    suspend fun addToFavorites(stock: StockLocalModel) {
        stock.isFavorite = true
        updateStockData(stock)
    }

    suspend fun removeFromFavorites(stock: StockLocalModel) {
        stock.isFavorite = false
        updateStockData(stock)
    }

    suspend fun updateStockOwnedAmount(stockName: String, newAmount: Long) {
        withContext(Dispatchers.IO) {
            val localStockData = localDataSource.getStockByName(stockName)
            localStockData.ownedAmount = newAmount
            localDataSource.updateStockData(localStockData)
        }
    }

    /**
     * This function fetches the up-to-date stock data from a remote API.
     * It then updates the current price of the requested stock and
     * returns the fetched data.
     * **/
    suspend fun fetchRemoteStockData(stockTicker: String): Resource<StockRemoteModel> {
        val remoteStockDataResource: Resource<StockRemoteModel>
        withContext(Dispatchers.IO) {
            remoteStockDataResource = remoteDataSource.getStockData(stockTicker)
            if (remoteStockDataResource.status is Success) {
                val localStockData = getStockByTicker(stockTicker)
                localStockData.currentPrice = remoteStockDataResource.status.data?.currentPrice
                    ?: localStockData.currentPrice
                updateStockData(localStockData)
            }
        }
        return remoteStockDataResource
    }
}
