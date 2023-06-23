package com.example.stoks.data.local

import com.example.stoks.R


class StocksDataMaps {

    companion object {

        public val stockSymbols = mutableMapOf(
            "Apple" to "AAPL",
            "Google" to "GOOG",
            "Microsoft" to "MSFT",
            "Amazon" to "AMZN",
            "Tesla" to "TSLA",
            "Intel" to "INTC",
            "Netflix" to "NFLX",
            "Facebook" to "META"
        )

        public val stockPrices = mutableMapOf(
            "Apple" to doubleArrayOf(178.44, 179.97, 139.90, 132.87, 75.13, 77.81),
            "Google" to doubleArrayOf(124.93, 123.51, 146.13, 147.31, 90.87, 92.01),
            "Microsoft" to doubleArrayOf(320.03, 318.60, 410.19, 407.38, 110.12, 110.13),
            "Amazon" to doubleArrayOf(114.27, 116.77, 127.13, 127.71, 99.91, 99.82),
            "Tesla" to doubleArrayOf(186.20, 180.70, 210.56, 209.99, 101.23, 101.78),
            "Intel" to doubleArrayOf(31.72, 32.31, 72.65 ,73.98, 74.90, 75.13)
        )


        public val stockImages = mutableMapOf<String, Int?>(
            "Apple" to R.drawable.apple_image,
            "Google" to R.drawable.google_image,
            "Microsoft" to R.drawable.microsoft_image,
            "Amazon" to R.drawable.amazon_image,
            "Tesla" to R.drawable.tesla_image,
            "Intel" to R.drawable.intel_image,
            "Netflix" to R.drawable.netflix_image,
            "Facebook" to R.drawable.facebook_image
        )

    }



}