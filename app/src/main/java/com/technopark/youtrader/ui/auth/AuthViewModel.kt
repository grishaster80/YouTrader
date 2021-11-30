package com.technopark.youtrader.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.base.Event
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.network.IAuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: IAuthService
) : BaseViewModel() {

    fun navigateToCurrenciesFragment() {
        navigateTo(AuthFragmentDirections.actionAuthFragmentToCurrenciesFragment())
    }

    fun navigateToRegFragment() {
        navigateTo(AuthFragmentDirections.actionAuthFragmentToRegFragment())
    }
}
