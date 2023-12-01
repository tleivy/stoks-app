package com.example.stoks.data.remote_db

import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.utils.Resource
import com.example.stoks.data.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRemoteDataSource @Inject constructor(
    private val stockRemoteService: StockRemoteService
) : ApiCallExecutor() {

    suspend fun getStockData(stockTicker: String): Resource<StockRemoteModel> {
        return getResult { stockRemoteService.getQuote(stockTicker, Constants.API_KEY) }
    }
}