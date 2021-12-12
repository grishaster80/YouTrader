package com.technopark.youtrader.model

import androidx.room.Entity
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "CryptoCurrencyTransaction", primaryKeys = ["id", "timestamp"])
@IgnoreExtraProperties
data class CryptoCurrencyTransaction(
    val id: String = "-1",
    // TODO check timestamp timezone
    val timestamp: Long = 0,
    val amount: Double = 0.0,
    val price: Double = 0.0
)
