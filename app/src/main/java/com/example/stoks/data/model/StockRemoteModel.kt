package com.example.stoks.data.model

import com.google.gson.annotations.SerializedName

data class StockRemoteModel(
    @SerializedName("c") val currentPrice: Double,
    @SerializedName("h") val highPriceOfDay: Double,
    @SerializedName("l") val lowPriceOfDay: Double,
    @SerializedName("o") val openPriceOfDay: Double,
    @SerializedName("pc") val previousClosePrice: Double,
    @SerializedName("t") val lastTradeTime: Long
)
