package com.technopark.youtrader.model

import com.technopark.youtrader.database.transaction_entity.LocalCryptoCurrencyTransaction

data class InfoCurrencyModel(
    var operationItemList: List<HistoryOperationItem> = listOf(),
    var cryptoCurrency: LocalCryptoCurrencyTransaction = LocalCryptoCurrencyTransaction(),
    var totalPrice: Double = 0.0,
    var totalAmount: Double = 0.0,
    var absChange: Double = 0.0,
    var relativeChange: Double = 0.0
)
