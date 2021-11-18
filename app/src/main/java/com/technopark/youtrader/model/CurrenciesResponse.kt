package com.technopark.youtrader.model

import com.squareup.moshi.Json

data class CurrenciesResponse(
    @Json(name = "data")val data: List<CryptoCurrency>,
    @Json(name = "timestamp")val timestamp: Long
)
