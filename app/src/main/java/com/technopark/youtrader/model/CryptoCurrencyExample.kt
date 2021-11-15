package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrencyExample(@PrimaryKey val id: Int)

data class CryptoCurrencyWrapper(
    @Json(name = "data")
    val data: CryptoCurrency,
    @Json(name = "timestamp")
    val timestamp: String,
)

data class CryptoCurrency(
    @Json(name = "id")
    val id: String,
    @Json(name = "rank")
    val rank: Int,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "supply")
    val supply: String,
    @Json(name = "maxSupply")
    val maxSupply: String,
    @Json(name = "marketCapUsd")
    val marketCapUsd: String,
    @Json(name = "volumeUsd24Hr")
    val volumeUsd24Hr: String,
    @Json(name = "priceUsd")
    val priceUsd: String,
    @Json(name = "changePercent24Hr")
    val changePercent24Hr: String,
    @Json(name = "vwap24Hr")
    val vwap24Hr: String,
    @Json(name = "explorer")
    val explorer: String
)
