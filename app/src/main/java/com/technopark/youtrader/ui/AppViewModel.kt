package com.technopark.youtrader.ui

import androidx.lifecycle.ViewModel
import com.technopark.youtrader.R

class AppViewModel : ViewModel() {

    fun chooseStartScreen(isPinSet: Boolean): Int {
        return if (isPinSet) {
            R.id.pinAuthFragment
        } else {
            R.id.authFragment
        }
    }
}
