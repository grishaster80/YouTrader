package com.technopark.youtrader.ui

import androidx.lifecycle.ViewModel
import com.technopark.youtrader.R

class AppViewModel : ViewModel() {

    fun chooseStartScreen(isAuthSet: Boolean, isPinSet: Boolean): Int {
        return if (isAuthSet) {
            if (isPinSet) {
                R.id.pinAuthFragment
            } else {
                R.id.currenciesFragment
            }
        } else {
            R.id.authFragment
        }
    }
}
