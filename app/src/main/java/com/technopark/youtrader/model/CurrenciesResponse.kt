package com.technopark.youtrader.model

data class CurrenciesResponse(
    val data: List<CryptoCurrency>,
    val timestamp: Long
)
