package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrency(@PrimaryKey
                                 @SerializedName("id")val id: String = "",
                          @SerializedName("symbol")val symbol: String = "",
                          @SerializedName("name")val name: String = "Error",
                          @SerializedName("priceUsd")val priceUsd: Double = 429.0,
                          @SerializedName("changePercent24Hr")val changePercent24Hr: Double = 0.0)
