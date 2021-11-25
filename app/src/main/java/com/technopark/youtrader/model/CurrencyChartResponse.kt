package com.technopark.youtrader.model

data class CurrencyChartResponse(
    val data: List<CurrencyChartElement>,
    val timestamp: Long
)
