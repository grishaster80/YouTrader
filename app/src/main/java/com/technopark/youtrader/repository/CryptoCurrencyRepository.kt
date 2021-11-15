package com.technopark.youtrader.repository

import android.util.Log
import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.model.CryptoCurrencyExample
import com.technopark.youtrader.network.CryptoCurrencyNetworkService
import com.technopark.youtrader.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoCurrencyRepository @Inject constructor(
    private val networkService: CryptoCurrencyNetworkService,
    private val database: AppDatabase
) {
    fun getCurrencies(): Flow<List<CryptoCurrencyExample>> = flow {
        val currenciesFromNetwork = networkService.getCryptoCurrency()
        if (currenciesFromNetwork.isNotEmpty()) {
            emit(currenciesFromNetwork)
        } else {
            val currenciesFromDatabase = database.cryptoCurrencyDao().getCurrencies()
            emit(currenciesFromDatabase)
        }
    }

    fun getCurrency() {
        val response = networkService.getCurrency()
    }

    companion object {
        private const val TAG = "CryptoCurrencyRepositor"
    }
}
