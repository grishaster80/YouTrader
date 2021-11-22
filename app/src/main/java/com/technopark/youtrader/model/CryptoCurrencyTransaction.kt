package com.technopark.youtrader.model

import androidx.room.Entity

@Entity(tableName = "CryptoCurrencyTransaction")
data class CryptoCurrencyTransaction(
    val id: String,
    val price: Double,
    val amount: Double,
    // TODO check timestamp timezone
    val data: Long
)
