package com.technopark.youtrader.ui.profile

import android.util.Log
import android.widget.Toast
import com.technopark.youtrader.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {

    fun navigateToPinRegFragment() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToPinRegFragment())
    }
}
