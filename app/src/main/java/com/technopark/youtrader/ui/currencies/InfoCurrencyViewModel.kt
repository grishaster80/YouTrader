package com.technopark.youtrader.ui.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.database.CryptoCurrencyDao_Impl
import com.technopark.youtrader.database.transaction_entity.LocalCryptoCurrencyTransaction
import com.technopark.youtrader.database.transaction_entity.Transaction
import com.technopark.youtrader.model.CurrencyItem
import com.technopark.youtrader.model.HistoryOperation
import com.technopark.youtrader.model.HistoryOperationItem
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import com.technopark.youtrader.repository.CryptoTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowWith
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoCurrencyViewModel @Inject constructor(
    private val repository: CryptoTransactionRepository
) : BaseViewModel() {

    private var currencyTransactionItems: List<HistoryOperationItem> = listOf()
    private val _screenState = MutableLiveData<Result<List<HistoryOperationItem>>>()
    val screenState: LiveData<Result<List<HistoryOperationItem>>> = _screenState

    val currency = MutableLiveData<LocalCryptoCurrencyTransaction>();

    init {
        viewModelScope.launch {
            _screenState.value = Result.Loading
            //repository.insertCurrency("1","Bitcoin","BTC");

            //repository.insertTransaction(12.22882,4385.0,"1")
            //repository.insertTransaction(0.22200,435.76,"1")
        }
    }

    fun updateCurrencyTransactions(currencyId: String) {

        viewModelScope.launch {
            var sym: String = ""
            repository.getCurrency(currencyId).collect {
                curr -> sym = curr.symbol
            }
            repository.getAllCurrencyTransaction(currencyId)
                .catch { error ->
                    _screenState.value = Result.Error(error)
                }
                .collect { currencyTransactions ->
                    currencyTransactionItems =
                        currencyTransactions.map { transaction -> HistoryOperationItem(transaction,sym) }
                    _screenState.value = Result.Success(currencyTransactionItems)
                }
        }
    }

    fun updateCurrency(id: String) {
        viewModelScope.launch {
        repository.getCurrency(id)
            .collect {
                curr -> currency.value = curr
            }
        }
    }

    companion object {
        private const val TAG = "InfoCurrencyViewModel"
    }
}
