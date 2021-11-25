package com.technopark.youtrader.ui.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository
) : BaseViewModel() {
    private val _chartElements = MutableLiveData<List<CurrencyChartElement>>()
    val chartElements: LiveData<List<CurrencyChartElement>> = _chartElements

    fun updateCurrencyChartHistory(id: String){
        viewModelScope.launch {
            repository.getCurrencyChartHistoryById(id)
                .collect { currencies ->
                    _chartElements.value = currencies
                }
        }
    }
}
