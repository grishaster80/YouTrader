package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.network.CryptoCurrencyNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CryptoCurrencyRepository @Inject constructor(
    private val networkService: CryptoCurrencyNetworkService,
    private val database: AppDatabase
) {
    suspend fun getCurrencies(): List<CryptoCurrency> = withContext(Dispatchers.IO) {
        val currenciesFromNetwork = networkService.getCryptoCurrency()
        if (currenciesFromNetwork.isNotEmpty()) {
            return@withContext currenciesFromNetwork
        } else {
            val currenciesFromDatabase = database.cryptoCurrencyDao().getCurrencies()
            return@withContext currenciesFromDatabase
        }
    }

    fun getCurrency() {
        val response = networkService.getCurrency()
    }

    companion object {
        private const val TAG = "CryptoCurrencyRepositor"
    }
}
