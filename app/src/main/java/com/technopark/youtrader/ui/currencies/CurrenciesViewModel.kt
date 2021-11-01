package com.technopark.youtrader.ui.currencies

import com.technopark.youtrader.base.BaseViewModel

class CurrenciesViewModel : BaseViewModel() {

    fun navigateToFirstFragment() {
        navigateTo(SecondFragmentDirections.actionSecondFragmentToAuthFragment())
    }

    fun navigateToProfileFragment() {
        navigateTo(SecondFragmentDirections.actionSecondFragmentToProfileFragment())
    }
}
