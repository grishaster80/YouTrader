package com.technopark.youtrader.model

import androidx.room.Entity

@Entity(tableName = "CryptoCurrencyTransaction", primaryKeys = ["id", "timestamp"])
data class CryptoCurrencyTransaction(
    val id: String,
    // TODO check timestamp timezone
    val timestamp: Long,
    val amount: Double,
    val price: Double
)
