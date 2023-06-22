package com.example.stoks.data.remote_db

import com.example.stoks.data.model.StockData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {
    @GET("quote")
    fun getQuote(@Query("symbol") symbol: String): Response<StockData>
}