package com.technopark.youtrader.ui.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.model.CurrencyItem
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.network.NetworkResponse
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository
) : BaseViewModel() {

    private var currencyItems: List<CurrencyItem> = listOf()
    private val _screenState = MutableLiveData<Result<List<CurrencyItem>>>()
    val screenState: LiveData<Result<List<CurrencyItem>>> = _screenState

    init {
        viewModelScope.launch {
            _screenState.value = Result.Loading
            when (val response = repository.getCurrencies()) {
                is NetworkResponse.Success -> {
                    currencyItems = (response.value as List<*>).map { cryptoCurrency ->
                        CurrencyItem(cryptoCurrency as CryptoCurrency)
                    }
                    _screenState.value =
                        Result.Success(currencyItems)
                }
                is NetworkResponse.ApiError -> {
                    _screenState.value = Result.Error(IllegalArgumentException())
                }
                is NetworkResponse.NetworkError -> {
                    _screenState.value = Result.Error(UnknownHostException())
                }
                is NetworkResponse.UnknownError -> {
                    _screenState.value = Result.Error(Exception())
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
                _screenState.value = Result.Loading
                _screenState.value = Result.Success(
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
