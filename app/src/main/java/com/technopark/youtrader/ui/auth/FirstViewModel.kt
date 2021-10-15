package com.technopark.youtrader.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.model.CryptoCurrencyExample
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository
): ViewModel() {

    private var _cryptoCurrencies:  MutableLiveData<List<CryptoCurrencyExample>> = MutableLiveData()
    val cryptoCurrencies: LiveData<List<CryptoCurrencyExample>> = _cryptoCurrencies

    fun getCryptoCurrencies() {
        viewModelScope.launch {
            repository.getCurrencies()
                .collect { cryptoCurrencies ->
                    _cryptoCurrencies.value = cryptoCurrencies
                }
        }
    }
}