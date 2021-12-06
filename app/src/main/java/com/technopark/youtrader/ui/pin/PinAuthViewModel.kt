package com.technopark.youtrader.ui.pin

import com.technopark.youtrader.base.BaseViewModel

class PinAuthViewModel : BaseViewModel() {

    fun navigateToCurrenciesFragment() {
        navigateTo(PinAuthFragmentDirections.actionPinAuthFragmentToCurrenciesFragment())
    }
}
