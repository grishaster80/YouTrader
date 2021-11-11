package com.technopark.youtrader.model

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("data")val data: List<CryptoCurrency>,
                @SerializedName("timestamp")val timestamp: Long)



