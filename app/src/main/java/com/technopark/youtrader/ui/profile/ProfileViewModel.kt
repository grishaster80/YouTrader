package com.technopark.youtrader.ui.profile

import com.technopark.youtrader.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {

    fun navigateToPinRegFragment() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToPinRegFragment())
    }
}
