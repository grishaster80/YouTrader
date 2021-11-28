package com.technopark.youtrader.database.transaction_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "currencyTransaction",
    foreignKeys = [
        ForeignKey(
            entity = LocalCryptoCurrencyTransaction::class,
            parentColumns = ["id"],
            childColumns = ["cryptoCurrencyId"]
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val price: Double,
    val amount: Double,

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val timestamp: String? = null,

    val cryptoCurrencyId: String
)
