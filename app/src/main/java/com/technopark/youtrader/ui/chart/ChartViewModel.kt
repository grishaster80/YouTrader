package com.technopark.youtrader.ui.chart

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.network.firebase.IFirebaseRepository
import com.technopark.youtrader.repository.ChartHistoryRepository
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import com.technopark.youtrader.repository.CryptoTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val chartHistoryRepository: ChartHistoryRepository,
    private val transactionRepository: CryptoTransactionRepository,
    private val apiRepository: CryptoCurrencyRepository,
    private val firebaseRepository: IFirebaseRepository
) : BaseViewModel() {
    private var chartElements: List<CurrencyChartElement> = listOf()
    private val _screenState = MutableLiveData<Result<List<CurrencyChartElement>>>()
    val screenState: LiveData<Result<List<CurrencyChartElement>>> = _screenState
    private val _currentPrice = MutableLiveData<Result<Double>>()
    val currentPrice: LiveData<Result<Double>> = _currentPrice

    fun updatePrice(currencyId: String) {
        _currentPrice.value = Result.Loading
        viewModelScope.launch {
            apiRepository.getCurrencyById(currencyId)
                .catch { error ->
                    _currentPrice.value = Result.Error(error)
                }
                .collect {
                    _currentPrice.value =  Result.Success(it.priceUsd)
            }
        }
    }

    fun getCurrencyChartHistory(id: String?, interval: String?) {
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
    fun getDatabaseCurrencyChartHistory(id: String?, interval: String?) {
        _screenState.value = Result.Loading
        viewModelScope.launch {
            chartHistoryRepository.getDatabaseChartHistoryById(id, interval)
                .catch { error ->
                    _screenState.value = Result.Error(error)
                }
                .collect { elements ->
                    chartElements = elements
                    _screenState.value = Result.Success(chartElements)
                }
        }
    }
    fun deleteAll() {
        viewModelScope.launch {
            chartHistoryRepository.deleteAllCharts()
        }
    }

    fun buyCryptoCurrency(id: String, amount: Double){
        viewModelScope.launch {
            var newPrice: Double? = null
            _currentPrice.value = Result.Loading
            apiRepository.getCurrencyById(id)
                .catch { error ->
                    _currentPrice.value = Result.Error(error)
                }
                .collect {
                    _currentPrice.value = Result.Success(it.priceUsd)
                    newPrice = it.priceUsd*amount
                }
            if ( newPrice != null) {
                transactionRepository.insertTransaction(id, amount, newPrice!! )
                firebaseRepository.insertTransaction(id,amount,newPrice!!)
            }
        }
    }
}
