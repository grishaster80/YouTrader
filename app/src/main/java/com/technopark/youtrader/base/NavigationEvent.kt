package com.technopark.youtrader.base

import androidx.navigation.NavDirections

sealed class NavigationEvent {
    data class ToDirection(val direction: NavDirections) : NavigationEvent()
    object Up : NavigationEvent()
    object Back : NavigationEvent()
}
