package com.technopark.youtrader.ui.pin

import com.technopark.youtrader.base.BaseViewModel

class PinRegViewModel : BaseViewModel() {

    fun navigateToProfileFragment() {
        navigateTo(PinRegFragmentDirections.actionPinRegFragmentToProfileFragment())
    }
}
