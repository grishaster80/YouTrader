package com.technopark.youtrader.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

abstract class BaseViewModel : ViewModel() {

    private val _events: MutableLiveData<Event<NavigationEvent>> = MutableLiveData()
    val events = _events

    protected fun navigateTo(direction: NavDirections) {
        _events.postValue(Event(NavigationEvent.ToDirection(direction)))
    }

    protected fun navigateUp() {
        _events.postValue(Event(NavigationEvent.Up))
    }

    protected fun navigateBack() {
        _events.postValue(Event(NavigationEvent.Back))
    }

    fun goUp() {
        navigateUp()
    }

    fun goBack() {
        navigateBack()
    }
}
