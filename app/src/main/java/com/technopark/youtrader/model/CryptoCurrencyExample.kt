package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Resp(@SerializedName("data")val data: List<CryptoCurrencyExample>,
                @SerializedName("timestamp")val timestamp: String)


@Entity(tableName = "cryptocurrencyexample")
data class CryptoCurrencyExample(@PrimaryKey
                                 @SerializedName("id")val id: String = "",
                                 @SerializedName("rank")val rank: String = "",
                                 @SerializedName("symbol")val symbol: String = "",
                                 @SerializedName("name")val name: String = "",
                                 @SerializedName("supply")val supply: String = "",
                                 @SerializedName("maxSupply")val maxSupply: String? = null,
                                 @SerializedName("marketCapUsd")val marketCapUsd: String = "",
                                 @SerializedName("volumeUsd24Hr")val volumeUsd24Hr: String = "",
                                 @SerializedName("priceUsd")val priceUsd: String = "",
                                 @SerializedName("changePercent24Hr")val changePercent24Hr: String = "",
                                 @SerializedName("vwap24Hr")val vwap24Hr: String = "")
