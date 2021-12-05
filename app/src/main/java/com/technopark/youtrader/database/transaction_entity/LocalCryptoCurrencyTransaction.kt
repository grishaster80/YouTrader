package com.technopark.youtrader.database.transaction_entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalCryptoCurrencyTransaction")
data class LocalCryptoCurrencyTransaction(
    @PrimaryKey(autoGenerate = false)
    val id: String = "-1",
    val symbol: String = "",
    val name: String = "",

)
