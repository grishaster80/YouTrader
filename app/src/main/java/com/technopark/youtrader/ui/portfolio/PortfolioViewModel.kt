package com.technopark.youtrader.ui.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.PortfolioItem
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.repository.CryptoTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val repository: CryptoTransactionRepository
) : BaseViewModel() {

    private var portfolioItems: List<PortfolioItem> = listOf()
    private val _screenState = MutableLiveData<Result<List<PortfolioItem>>>()
    val screenState: LiveData<Result<List<PortfolioItem>>> = _screenState

    init {
        viewModelScope.launch {
            _screenState.value = Result.Loading
            repository.getPortfolioCurrencies()
                .collect { currencies ->
                    // TODO API request with to get current price
                    //  val ids = currencies.map{ it.id }

                    // TODO update portfolio_value
                    //  val totalPrice = currencies.sumOf { it.price }

                    portfolioItems = currencies.map { PortfolioItem(it) }
                    _screenState.value = Result.Success(portfolioItems)
                }
        }
    }

    fun navigateToInfoHistoryFragment(currencyId: String) {
        navigateTo(
            PortfolioFragmentDirections.actionPortfolioFragmentToInfoHistoryFragment(currencyId)
        )
    }
}
