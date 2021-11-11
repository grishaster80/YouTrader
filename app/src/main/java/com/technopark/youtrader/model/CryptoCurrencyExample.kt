package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Resp(@SerializedName("data")val data: List<CryptoCurrencyExample>,
                @SerializedName("timestamp")val timestamp: Long)


@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrencyExample(@PrimaryKey
                                 @SerializedName("id")val id: String = "",
                                 @SerializedName("symbol")val symbol: String = "",
                                 @SerializedName("name")val name: String = "",
                                 @SerializedName("priceUsd")val priceUsd: Double = 0.0,
                                 @SerializedName("changePercent24Hr")val changePercent24Hr: Double = 0.0)
