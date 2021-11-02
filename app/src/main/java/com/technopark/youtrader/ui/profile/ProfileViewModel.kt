package com.technopark.youtrader.ui.profile

import com.technopark.youtrader.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {

    fun navigateToCurrenciesFragment() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToCurrenciesFragment())
    }

    fun navigateToWithoutBottomNavViewFragment() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToWithoutBottomNavViewFragment())
    }
}
