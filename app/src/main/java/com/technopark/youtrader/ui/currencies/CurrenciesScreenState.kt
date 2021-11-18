package com.technopark.youtrader.ui.currencies

import com.technopark.youtrader.model.CurrencyItem

sealed class CurrenciesScreenState {
    data class Success(val data: List<CurrencyItem>) : CurrenciesScreenState()
    data class Error(val message: String) : CurrenciesScreenState()
    object Loading : CurrenciesScreenState()
}
