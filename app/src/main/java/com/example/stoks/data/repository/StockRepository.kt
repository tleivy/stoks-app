package com.example.stoks.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.stoks.data.local.StockLocalDataSource
import com.example.stoks.data.model.StockLocalModel
import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.remote_db.StockRemoteDataSource
import com.example.stoks.data.utils.Loading
import com.example.stoks.data.utils.Resource
import com.example.stoks.data.utils.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StockRepository @Inject constructor(
    private val localDataSource: StockLocalDataSource,
    private val remoteDataSource: StockRemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun getAllStocks(): LiveData<List<StockLocalModel>> {
        val stocksList: LiveData<List<StockLocalModel>>
        withContext(defaultDispatcher) {
            stocksList = localDataSource.getAllStocks()
        }
        return stocksList
    }

    suspend fun getFavoriteStocks(): LiveData<List<StockLocalModel>> {
        val favoriteStocksList: LiveData<List<StockLocalModel>>
        withContext(defaultDispatcher) {
            favoriteStocksList = localDataSource.getAllStocks()
        }
        return favoriteStocksList
    }

    suspend fun getStockByName(name: String): StockLocalModel? {
        val stock: StockLocalModel?
        withContext(defaultDispatcher) {
            stock = localDataSource.getStockByName(name)
        }
        return stock
    }

    suspend fun getStockByTicker(ticker: String): StockLocalModel? {
        val stock: StockLocalModel?
        withContext(defaultDispatcher) {
            stock = localDataSource.getStockByTicker(ticker)
        }
        return stock
    }

    suspend fun addNewStock(stock: StockLocalModel) {
        withContext(defaultDispatcher) {
            localDataSource.addNewStock(stock)
        }
    }

    suspend fun deleteStock(stock: StockLocalModel) {
        withContext(defaultDispatcher) {
            localDataSource.deleteStock(stock)
        }
    }

    suspend fun deleteAllStocks() {
        withContext(defaultDispatcher) {
            localDataSource.deleteAllStocks()
        }
    }

    suspend fun updateStockData(stock: StockLocalModel) {
        withContext(defaultDispatcher) {
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
        withContext(defaultDispatcher) {
            val localStockData = localDataSource.getStockByName(stockName)
            localStockData?.let { stock ->
                stock.ownedAmount = newAmount
                localDataSource.updateStockData(stock)
            }
        }
    }

    /**
     * This function fetches the up-to-date stock data from a remote API.
     * It then updates the current price of the requested stock and
     * returns the fetched data.
     * **/
    suspend fun fetchRemoteStockData(stockTicker: String): Resource<StockRemoteModel> {
        val remoteStockDataResource: Resource<StockRemoteModel>
        withContext(defaultDispatcher) {
            remoteStockDataResource = remoteDataSource.getStockData(stockTicker)
            if (remoteStockDataResource.status is Success) {
                getStockByTicker(stockTicker)?.let { stock ->
                    stock.currentPrice = remoteStockDataResource.status.data?.currentPrice
                        ?: stock.currentPrice
                    updateStockData(stock)
                }
            }
        }
        return remoteStockDataResource
    }
}
