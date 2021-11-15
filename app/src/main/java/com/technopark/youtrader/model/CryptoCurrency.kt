package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrency(
    @PrimaryKey
    @field:Json(name = "id") val id: String = "",
    @field:Json(name = "symbol") val symbol: String = "",
    @field:Json(name = "name") val name: String = "Error",
    @field:Json(name = "priceUsd") val priceUsd: Double = 429.0,
    @field:Json(name = "changePercent24Hr") val changePercent24Hr: Double = 0.0
)
