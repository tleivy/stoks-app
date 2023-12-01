package com.example.stoks.data.remote_db

import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRemoteDataSource @Inject constructor(
    private val stockService: StockService
) : BaseDataSource() {

    suspend fun getStockData(stockTicker: String, apiToken: String): Resource<StockRemoteModel> {
        return getResult { stockService.getQuote(stockTicker, apiToken) }
    }
}