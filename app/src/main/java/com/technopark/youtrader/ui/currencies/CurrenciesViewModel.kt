package com.technopark.youtrader.ui.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.model.CurrencyItem
import com.technopark.youtrader.network.NetworkResponse
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository
) : BaseViewModel() {

    private var currencyItems: List<CurrencyItem> = listOf()
    private val _screenState = MutableLiveData<CurrenciesScreenState>()
    val screenState: LiveData<CurrenciesScreenState> = _screenState

    init {
        viewModelScope.launch {
            _screenState.value = CurrenciesScreenState.Loading
            when (val response = repository.getCurrencies()) {
                is NetworkResponse.Success -> {
                    currencyItems = (response.body as List<*>).map { cryptoCurrency ->
                        CurrencyItem(cryptoCurrency as CryptoCurrency)
                    }
                    _screenState.value =
                        CurrenciesScreenState.Success(currencyItems)
                }
                is NetworkResponse.ApiError -> {
                    _screenState.value = CurrenciesScreenState.Error("Ошибка сервера")
                }
                is NetworkResponse.NetworkError -> {
                    _screenState.value = CurrenciesScreenState.Error("Отсутствует сетевое подключение")
                }
                is NetworkResponse.UnknownError -> {
                    _screenState.value = CurrenciesScreenState.Error("Неизвестная ошибка")
                }
            }
        }
    }

    fun navigateToChartFragment() {
        navigateTo(CurrenciesFragmentDirections.actionCurrenciesFragmentToChartFragment())
    }

    fun updateCurrenciesByMatch(pattern: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _screenState.value = CurrenciesScreenState.Loading
                _screenState.value = CurrenciesScreenState.Success(
                    currencyItems.filter { currencyItem ->
                        currencyItem.currency.id
                            .contains(
                                pattern,
                                true
                            )
                    }
                )
            }
        }
    }

    companion object {
        private const val TAG = "CurrenciesViewModel"
    }
}
