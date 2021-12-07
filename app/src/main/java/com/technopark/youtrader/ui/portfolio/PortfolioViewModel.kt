package com.technopark.youtrader.ui.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.*
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import com.technopark.youtrader.repository.CryptoTransactionRepository
import com.technopark.youtrader.ui.currencies.InfoCurrencyViewModel
import com.technopark.youtrader.utils.Constants.Companion.PERCENTAGE_PRECISION
import com.technopark.youtrader.utils.Constants.Companion.SIMPLE_PRECISION
import com.technopark.youtrader.utils.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val repository: CryptoTransactionRepository,
    private val apiRepository: CryptoCurrencyRepository
) : BaseViewModel() {

    private var portfolioInfoModel: PortfolioInfoModel = PortfolioInfoModel()
    private var currenciesList:  List<PortfolioCurrencyInfo> = listOf()
    private val _screenState = MutableLiveData<Result<PortfolioInfoModel>>()
    val screenState: LiveData<Result<PortfolioInfoModel>> = _screenState

    init {
        viewModelScope.launch {
            _screenState.value = Result.Loading
            repository.getPortfolioCurrencies()
                .collect { currencies ->
                    currenciesList = currencies
                    portfolioInfoModel.currencies = currencies.map {
                        oldCurrency ->
                        createPortfolioItem(oldCurrency)
                    }
                }
            _screenState.value = Result.Loading
            updatePorfolioInfo()
            _screenState.value = Result.Success(portfolioInfoModel)
        }
    }

    private suspend fun updatePorfolioInfo() {
        portfolioInfoModel.totalPrice = 0.0;
        val oldTotalPrice = currenciesList.sumOf { it.price }
        currenciesList.map {
            oldCurrency ->
            apiRepository.getCurrencyById(oldCurrency.id).collect { cryptoCurrency ->
                portfolioInfoModel.totalPrice += cryptoCurrency.priceUsd * oldCurrency.amount
                portfolioInfoModel.absChange += calcProfit(oldCurrency.amount,cryptoCurrency.priceUsd,oldCurrency.price)
            }
            portfolioInfoModel.relativeChange = asPercent(calcProfitPercentage(portfolioInfoModel.absChange,oldTotalPrice))
        }
    }

    private suspend fun createPortfolioItem(oldCurrency: PortfolioCurrencyInfo): PortfolioItem {
        var portfolioItem: PortfolioItem? = null
        apiRepository.getCurrencyById(oldCurrency.id).collect {
                cryptoCurrency ->
                    var newPrice = cryptoCurrency.priceUsd*oldCurrency.amount
                    var item = PortfolioCurrencyInfo(oldCurrency.id,oldCurrency.amount,newPrice)
                    val change = roundTo(calcProfit(item.amount,newPrice,oldCurrency.price),SIMPLE_PRECISION) + " (" +
                            roundTo(asPercent(calcProfitPercentage(item.amount,newPrice,oldCurrency.price)),
                                PERCENTAGE_PRECISION) + "%)"
            portfolioItem = PortfolioItem(item,change)
        }
        return if (portfolioItem != null) {
            portfolioItem as PortfolioItem
        } else {
            PortfolioItem(oldCurrency,"")
        }
    }

    private fun calcProfit(amount: Double, price: Double, oldTotalPrice: Double): Double {
        return amount * price - oldTotalPrice
    }

    private  fun calcProfitPercentage(amount: Double, price: Double, oldTotalPrice: Double): Double{
        return calcProfit(amount,price,oldTotalPrice) / oldTotalPrice
    }

    private  fun calcProfitPercentage(change: Double, oldTotalPrice: Double): Double{
        return change / oldTotalPrice
    }

    private fun asPercent(number: Double): Double {
        return number * MULTIPLY_NUM
    }

    fun navigateToInfoHistoryFragment(currencyId: String) {
        navigateTo(
            PortfolioFragmentDirections.actionPortfolioFragmentToInfoHistoryFragment(currencyId)
        )
    }

    companion object {
        private const val TAG = "PortfolioViewModel"
        private const val MULTIPLY_NUM = 100.0
    }
}
