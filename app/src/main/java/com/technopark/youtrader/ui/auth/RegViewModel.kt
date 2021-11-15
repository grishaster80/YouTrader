package com.technopark.youtrader.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CryptoCurrencyExample
import com.technopark.youtrader.network.IAuthService
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository,
    private val authService: IAuthService
) : BaseViewModel() {

    private var _cryptoCurrencies: MutableLiveData<List<CryptoCurrencyExample>> = MutableLiveData()
    val cryptoCurrencies: LiveData<List<CryptoCurrencyExample>> = _cryptoCurrencies

    fun getCryptoCurrencies() {
        viewModelScope.launch {
            repository.getCurrencies()
                .collect { cryptoCurrencies ->
                    _cryptoCurrencies.value = cryptoCurrencies
                }
        }
    }

    fun signUp(email: String, password: String) = authService.sighUp(email, password)

    fun signIn(email: String, password: String) = authService.signIn(email, password)

    fun checkSignIn(email: String): Boolean = authService.checkSignIn(email)

    fun signOut() = authService.sighOut()

    fun navigateToAuthFragment() {
        val someString = "Random text"
        navigateTo(RegFragmentDirections.actionRegFragmentToAuthFragment(someString))
    }
}