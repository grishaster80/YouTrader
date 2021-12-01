package com.technopark.youtrader.model

import androidx.room.Entity

@Entity(tableName = "CurrencyChartElement")
data class CurrencyChartElement(
    val priceUsd: String,
    val time: Long,
    val date: String
)
