package com.example.stoks.data.utils

import com.example.stoks.BuildConfig

class Constants {

    companion object {
        const val BASE_URL = "https://finnhub.io/api/v1/"
        const val API_KEY = BuildConfig.FINNHUB_API_KEY
        const val SYMBOL_QUERY_PARAM = "symbol"
        const val TOKEN_QUERY_PARAM = "token"
    }
}