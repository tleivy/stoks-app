package com.example.stoks

import com.example.stocks.R

class StocksDataMaps {

    companion object {

        public val stockSymbols = mutableMapOf(
            "Apple" to "AAPL",
            "Google" to "GOOG",
            "Microsoft" to "MSFT",
            "Amazon" to "AMZN",
            "Tesla" to "TSLA",
            "Intel" to "INTC"
        )

        public val stockPrices = mutableMapOf(
            "Apple" to doubleArrayOf(173.13, 173.98, 50.0),
            "Google" to doubleArrayOf(124.93, 123.51, 50.0),
            "Microsoft" to doubleArrayOf(320.03, 318.60, 50.0),
            "Amazon" to doubleArrayOf(114.27, 116.77, 50.0),
            "Tesla" to doubleArrayOf(186.20, 180.70, 50.0),
            "Intel" to doubleArrayOf(100.50,35.0,10.0)
        )


        public val stockImages = mutableMapOf<String, Int?>(
            "Apple" to R.drawable.apple_image,
            "Google" to R.drawable.google_image,
            "Microsoft" to R.drawable.microsoft_image,
            "Amazon" to R.drawable.amazon_image,
            "Tesla" to R.drawable.tesla_image,
            "Intel" to R.drawable.intel_image
        )

        public val stocks = mutableSetOf<String>(
            "Apple","Google","Microsoft","Amazon","Tesla","Intel"
        )


    }



}