package com.example.stoks.data.remote_db

import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.utils.Resource
import com.example.stoks.data.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRemoteDataSource @Inject constructor(
    private val stockService: StockService
) : BaseDataSource() {

    suspend fun getStockData(stockTicker: String): Resource<StockRemoteModel> {
        return getResult { stockService.getQuote(stockTicker, Constants.API_KEY) }
    }
}