package com.technopark.youtrader.ui.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CurrencyItem
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
            try {
                repository.getCurrencies().collect { currencies ->
                    currencyItems =
                        currencies.map { cryptoCurrency -> CurrencyItem(cryptoCurrency) }
                    _screenState.value = Result.Success(currencyItems)
                }
            } catch (e: Exception) {
                _screenState.value = Result.Error(e)
            }
        }
    }

    fun navigateToChartFragment() {
        navigateTo(CurrenciesFragmentDirections.actionCurrenciesFragmentToChartFragment())
    }

    fun updateCurrenciesByMatch(pattern: String) {
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

    companion object {
        private const val TAG = "CurrenciesViewModel"
    }
}
