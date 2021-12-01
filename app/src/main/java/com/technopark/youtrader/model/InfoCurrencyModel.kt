package com.technopark.youtrader.model

import com.technopark.youtrader.database.transaction_entity.LocalCryptoCurrencyTransaction

data class InfoCurrencyModel  (
        var operationItemList: List<HistoryOperationItem>,
        var cryptoCurrency: LocalCryptoCurrencyTransaction,
        var totalPrice: Double,
        var totalAmount:Double,
        var absChange: Double,
        var relativeChange: Double
        )
