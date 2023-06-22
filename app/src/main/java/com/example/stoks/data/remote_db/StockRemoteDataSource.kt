package com.example.stoks.data.remote_db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRemoteDataSource @Inject constructor(
    private val stockService: StockService): BaseDataSource() {

        suspend fun getQuote(symbol: String) = getResult { stockService.getQuote(symbol) }
    }

