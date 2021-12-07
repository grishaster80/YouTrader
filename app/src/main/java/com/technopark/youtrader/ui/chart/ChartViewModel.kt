package com.technopark.youtrader.ui.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.repository.ChartHistoryRepository
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import com.technopark.youtrader.repository.CryptoTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val chartHistoryRepository: ChartHistoryRepository,
    private val transactionRepository: CryptoTransactionRepository,
    private val apiRepository: CryptoCurrencyRepository
) : BaseViewModel() {
    private var chartElements: List<CurrencyChartElement> = listOf()
    private val _screenState = MutableLiveData<Result<List<CurrencyChartElement>>>()
    val screenState: LiveData<Result<List<CurrencyChartElement>>> = _screenState

    fun updateCurrencyChartHistory(id: String?, interval: String?) {
        _screenState.value = Result.Loading
        viewModelScope.launch {
            chartHistoryRepository.getChartHistoryById(id, interval)
                .catch { error ->
                    _screenState.value = Result.Error(error)
                }
                .collect { elements ->
                    chartElements = elements
                    _screenState.value = Result.Success(chartElements)
                }
        }
    }

    fun buyCryptoCurrency(id: String, amount: Double){
        viewModelScope.launch {
            var newPrice: Double? = null
            apiRepository.getCurrencyById(id).collect {
                newPrice = it.priceUsd*amount
            }
            if ( newPrice != null) {
                transactionRepository.insertTransaction(id, amount, newPrice!! )
            }
        }
    }
}
