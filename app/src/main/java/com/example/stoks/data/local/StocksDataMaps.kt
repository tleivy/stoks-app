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
            "Facebook" to "META",
            "Nvidia" to "NVDA",
            "Samsung" to "SSNLF"
        )


        public val stockImages = mutableMapOf<String, Int?>(
            "Apple" to R.drawable.apple_image,
            "Google" to R.drawable.google_image,
            "Microsoft" to R.drawable.microsoft_image,
            "Amazon" to R.drawable.amazon_image,
            "Tesla" to R.drawable.tesla_image,
            "Intel" to R.drawable.intel_image,
            "Netflix" to R.drawable.netflix_image,
            "Facebook" to R.drawable.facebook_image,
            "Nvidia" to R.drawable.nvidia_image,
            "Samsung" to R.drawable.samsung_image
        )

    }


}