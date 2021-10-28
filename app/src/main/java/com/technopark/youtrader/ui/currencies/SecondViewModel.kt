package com.technopark.youtrader.ui.currencies

import com.technopark.youtrader.base.BaseViewModel

class SecondViewModel : BaseViewModel() {

    fun buttonToFirstFragmentClick() {
        navigateTo(SecondFragmentDirections.actionSecondFragmentToAuthFragment())
    }

    fun buttonToProfileFragmentClick() {
        navigateTo(SecondFragmentDirections.actionSecondFragmentToProfileFragment())
    }
}
