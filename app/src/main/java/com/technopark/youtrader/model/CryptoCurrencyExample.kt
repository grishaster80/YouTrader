package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrencyExample(@PrimaryKey val id: Int)

data class CryptoCurrencyWrapper(
    @field:Json(name = "data")
    val data: CryptoCurrency,
    @field:Json(name = "timestamp")
    val timestamp: String,
)

data class CryptoCurrency(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "rank")
    val rank: Int,
    @field:Json(name = "symbol")
    val symbol: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "supply")
    val supply: String,
    @field:Json(name = "maxSupply")
    val maxSupply: String,
    @field:Json(name = "marketCapUsd")
    val marketCapUsd: String,
    @field:Json(name = "volumeUsd24Hr")
    val volumeUsd24Hr: String,
    @field:Json(name = "priceUsd")
    val priceUsd: String,
    @field:Json(name = "changePercent24Hr")
    val changePercent24Hr: String,
    @field:Json(name = "vwap24Hr")
    val vwap24Hr: String,
    @field:Json(name = "explorer")
    val explorer: String
)
