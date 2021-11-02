package com.technopark.youtrader.ui.currencies

import com.technopark.youtrader.base.BaseViewModel

class CurrenciesViewModel : BaseViewModel() {

    fun navigateToAuthFragment() {
        navigateTo(CurrenciesFragmentDirections.actionCurrenciesFragmentToAuthFragment())
    }
}
