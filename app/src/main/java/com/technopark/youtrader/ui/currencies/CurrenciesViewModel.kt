package com.technopark.youtrader.ui.currencies

import androidx.lifecycle.*
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.model.CurrencyItem
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

    private val _currencyItems: MutableLiveData<List<CurrencyItem>> = liveData{
        emit(getCurrencyItems())
    } as MutableLiveData<List<CurrencyItem>>
    val currencyItems: LiveData<List<CurrencyItem>> = _currencyItems



    fun navigateToAuthFragment() {
        navigateTo(CurrenciesFragmentDirections.actionCurrenciesFragmentToAuthFragment())
    }

    fun navigateToWithoutBottomNavViewFragment() {
        navigateTo(
            CurrenciesFragmentDirections.actionCurrenciesFragmentToWithoutBottomNavViewFragment()
        )
    }

    private suspend fun getCurrencyItems(): List<CurrencyItem> = withContext(Dispatchers.IO){
        val currencyItems = mutableListOf<CurrencyItem>()
        for (currency in getCurrencies()) {
            currencyItems.add(CurrencyItem(currency))
        }
        return@withContext currencyItems
    }

    private suspend fun getCurrencies(): List<CryptoCurrency> = withContext(Dispatchers.IO) {
        return@withContext repository.getCurrencies()
    }

    companion object {
        private const val TAG = "CurrenciesViewModel"
    }
}
