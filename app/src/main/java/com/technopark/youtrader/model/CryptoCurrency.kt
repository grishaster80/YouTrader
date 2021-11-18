package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrency(
    @PrimaryKey
    @Json(name = "id") val id: String = "",
    @Json(name = "symbol") val symbol: String = "",
    @Json(name = "name") val name: String = "Error",
    @Json(name = "priceUsd") val priceUsd: Double = 429.0,
    @Json(name = "changePercent24Hr") val changePercent24Hr: Double = 0.0
)
