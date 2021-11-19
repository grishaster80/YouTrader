package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrency(
    @PrimaryKey
    val id: String = "",
    val symbol: String = "",
    val name: String = "Error",
    val priceUsd: Double = 429.0,
    val changePercent24Hr: Double = 0.0
)
