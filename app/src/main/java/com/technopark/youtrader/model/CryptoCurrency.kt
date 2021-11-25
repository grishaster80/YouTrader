package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CryptoCurrency")
data class CryptoCurrency(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: Double,
    val changePercent24Hr: Double
)
