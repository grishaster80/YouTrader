package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrencyExample(@PrimaryKey val id: Int)
