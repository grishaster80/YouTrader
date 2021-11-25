package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CryptoCurrencyTransaction")
data class CryptoCurrencyTransaction(
    @PrimaryKey
    val id: String,
    val price: Double,
    val amount: Double,
    // TODO check timestamp timezone
    val data: Long
)
