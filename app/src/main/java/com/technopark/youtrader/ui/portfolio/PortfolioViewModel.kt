package com.technopark.youtrader.ui.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.*
import com.technopark.youtrader.network.firebase.FirebaseRepository
import com.technopark.youtrader.network.firebase.IFirebaseRepository
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import com.technopark.youtrader.repository.CryptoTransactionRepository
import com.technopark.youtrader.utils.Constants.Companion.PERCENTAGE_PRECISION
import com.technopark.youtrader.utils.Constants.Companion.SIMPLE_PRECISION
import com.technopark.youtrader.utils.Constants.Companion.USD_SYMBOL
import com.technopark.youtrader.utils.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val repository: CryptoTransactionRepository,
    private val apiRepository: CryptoCurrencyRepository,
    private val firebaseRepository: IFirebaseRepository
) : BaseViewModel() {

    private var portfolioInfoModel: PortfolioInfoModel = PortfolioInfoModel()
    private var currenciesMap: MutableMap<String, CryptoCurrency> = mutableMapOf()
    private var currenciesList: List<PortfolioCurrencyInfo> = listOf()
    private val _screenState = MutableLiveData<Result<PortfolioInfoModel>>()
    val screenState: LiveData<Result<PortfolioInfoModel>> = _screenState

    private fun update() {
        viewModelScope.launch {
            _screenState.value = Result.Loading

            firebaseRepository.getPortfolioCurrencies()
                .collect { currencies ->
                    val ids = currencies.map { it.id }
                    apiRepository.getCurrenciesByIds(ids)
                    .catch { error ->
                        _screenState.value = Result.Error(error)
                    }
                    .collect {
                        it.map { currency ->
                            currenciesMap[currency.id] = currency
                        }
                    }
                    currenciesList = currencies
                    portfolioInfoModel.currencies = currencies.map {
                            oldCurrency ->
                        createPortfolioItem(oldCurrency)
                    }
                }
            updatePortfolioInfo()
            _screenState.value = Result.Success(portfolioInfoModel)
        }
    }

    init {
        firebaseRepository.addListener {
            update()
        }
    }

    private fun updatePortfolioInfo() {
        portfolioInfoModel.totalPrice = 0.0
        val oldTotalPrice = currenciesList.sumOf { it.price }

        currenciesList.map {
            oldCurrency ->
            val priceUsd = (currenciesMap[oldCurrency.id]?.priceUsd ?: 0.0)
            portfolioInfoModel.totalPrice += priceUsd * oldCurrency.amount
            portfolioInfoModel.absChange += calcProfit(oldCurrency.amount, priceUsd, oldCurrency.price)
        }
        portfolioInfoModel.relativeChange = asPercent(calcProfitPercentage(portfolioInfoModel.absChange, oldTotalPrice))
    }

    private fun createPortfolioItem(oldCurrency: PortfolioCurrencyInfo): PortfolioItem {
        val priceUsd = (currenciesMap[oldCurrency.id]?.priceUsd ?: 0.0)
        val newPrice = priceUsd * oldCurrency.amount
        val item = PortfolioCurrencyInfo(oldCurrency.id, oldCurrency.amount, newPrice)
        val change = roundTo(calcProfit(item.amount, priceUsd, oldCurrency.price), SIMPLE_PRECISION).plus(
            USD_SYMBOL) + " (" +
            roundTo(
                asPercent(calcProfitPercentage(item.amount, priceUsd, oldCurrency.price)),
                PERCENTAGE_PRECISION
            ) + "%)"
        return PortfolioItem(item, change)
    }

    private fun calcProfit(amount: Double, price: Double, oldTotalPrice: Double): Double {
        return amount * price - oldTotalPrice
    }

    private fun calcProfitPercentage(amount: Double, price: Double, oldTotalPrice: Double): Double {
        if (oldTotalPrice == 0.0) return 0.0
        return calcProfit(amount, price, oldTotalPrice) / oldTotalPrice
    }

    private fun calcProfitPercentage(change: Double, oldTotalPrice: Double): Double {
        if (oldTotalPrice == 0.0) return 0.0
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

    fun deleteAllCryptoCurrencyTransaction() {
        viewModelScope.launch {
            _screenState.value = Result.Loading
            repository.deleteAllCryptoCurrencyTransaction()
            _screenState.value = Result.Success(PortfolioInfoModel())
        }
    }

    companion object {
        private const val TAG = "PortfolioViewModel"
        private const val MULTIPLY_NUM = 100.0
    }
}
