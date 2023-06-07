package com.example.stoks

import com.example.stocks.R

class StocksDataMaps {

    companion object {

        public val stockSymbols = mutableMapOf(
            "Apple Inc." to "AAPL",
            "Google" to "GOOG",
            "Microsoft Corporation" to "MSFT",
            "Amazon.com, Inc." to "AMZN",
            "Tesla, Inc." to "TSLA",
            "Intel Corporation" to "INTC"
        )

        public val stockPrices = mutableMapOf(
            "Apple Inc." to doubleArrayOf(178.44, 179.97, 139.90, 132.87, 75.13, 77.81),
            "Google" to doubleArrayOf(124.93, 123.51, 146.13, 147.31, 90.87, 92.01),
            "Microsoft Corporation" to doubleArrayOf(320.03, 318.60, 410.19, 407.38, 110.12, 110.13),
            "Amazon.com, Inc." to doubleArrayOf(114.27, 116.77, 127.13, 127.71, 99.91, 99.82),
            "Tesla, Inc." to doubleArrayOf(186.20, 180.70, 210.56, 209.99, 101.23, 101.78),
            "Intel Corporation" to doubleArrayOf(31.72, 32.31, 72.65 ,73.98, 74.90, 75.13)
        )


        public val stockImages = mutableMapOf<String, Int?>(
            "Apple Inc." to R.drawable.apple_image,
            "Google" to R.drawable.google_image,
            "Microsoft Corporation" to R.drawable.microsoft_image,
            "Amazon.com, Inc." to R.drawable.amazon_image,
            "Tesla, Inc." to R.drawable.tesla_image,
            "Intel Corporation" to R.drawable.intel_image
        )

    }



}