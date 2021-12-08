package com.technopark.youtrader.model

data class PortfolioInfoModel(
    var currencies: List<PortfolioItem> = listOf(),
    var totalPrice: Double = 0.0,
    var absChange: Double = 0.0,
    var relativeChange: Double = 0.0
)
