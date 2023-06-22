package com.example.stoks.data.remote_db

import com.example.stoks.data.model.StockData
import com.example.stoks.data.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRemoteDataSource @Inject constructor(
    private val stockService: StockService
) : BaseDataSource() {

    suspend fun getQuote(symbol: String, token: String): Resource<StockData> {
        return getResult { stockService.getQuote(symbol, token) }
    }
}