package com.technopark.youtrader.model

import com.squareup.moshi.Json

data class Response(
    @field:Json(name = "data")val data: List<CryptoCurrency>,
    @field:Json(name = "timestamp")val timestamp: Long
)
