package com.technopark.youtrader.model

import androidx.room.Entity

@Entity(tableName = "Chart", primaryKeys = ["id", "interval", "time"])
data class Chart(
    val id: String,
    val interval: String,
    val timestamp: Long,
    val priceUsd: String,
    val time: Long,
    val date: String
)
