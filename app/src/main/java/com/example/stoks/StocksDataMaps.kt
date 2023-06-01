package com.example.stoks

import com.example.stocks.R

class StocksDataMaps {

    companion object {

        public val stockSymbols = mutableMapOf(
            "Apple" to "AAPL",
            "Google" to "GOOG",
            "Microsoft" to "MSFT",
            "Amazon" to "AMZN",
            "Tesla" to "TSLA"
        )

        public val stockPrices = mutableMapOf(
            "Apple" to doubleArrayOf(173.13, 173.98, 176.39),
            "Google" to doubleArrayOf(124.93, 123.51, 124.20),
            "Microsoft" to doubleArrayOf(320.03, 318.60, 316.74),
            "Amazon" to doubleArrayOf(114.27, 116.77, 118.16),
            "Tesla" to doubleArrayOf(186.20, 180.70, 177.17)
        )

        public val stockImages = mutableMapOf<String, Int?>(
            "Apple" to R.drawable.apple_image,
            "Google" to R.drawable.google_image,
            "Microsoft" to R.drawable.microsoft_image,
            "Amazon" to R.drawable.amazon_image,
            "Tesla" to R.drawable.tesla_image
        )

        public val followedStocks = mutableSetOf<String>()

        public val stocks = mutableSetOf<String>(
            "Apple","Google","Microsoft","Amazon","Tesla"
        )


    }



}