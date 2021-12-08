package com.technopark.youtrader.model



data class InfoCurrencyModel(
    var operationItemList: List<HistoryOperationItem> = listOf(),
    var cryptoCurrency: CryptoCurrency = CryptoCurrency("", "", "", 0.0, 0.0),
    var totalPrice: Double = 0.0,
    var totalAmount: Double = 0.0,
    var absChange: Double = 0.0,
    var relativeChange: Double = 0.0
)
