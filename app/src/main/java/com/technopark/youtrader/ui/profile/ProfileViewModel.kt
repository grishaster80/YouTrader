package com.technopark.youtrader.ui.profile

import com.technopark.youtrader.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {
    fun buttonToSecondFragmentClick() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToSecondFragment())
    }
}
