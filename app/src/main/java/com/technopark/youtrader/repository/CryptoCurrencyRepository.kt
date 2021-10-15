package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.model.CryptoCurrencyExample
import com.technopark.youtrader.network.CryptoCurrencyNetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoCurrencyRepository @Inject constructor(
    private val networkService: CryptoCurrencyNetworkService,
    private val database: AppDatabase
) {
    fun getCurrencies(): Flow<List<CryptoCurrencyExample>> = flow {
        val currenciesFromNetwork = networkService.getCryptoCurrency()
        if (currenciesFromNetwork.isSuccessful) {
            emit(currenciesFromNetwork.body()!!)
        } else {
            val currenciesFromDatabase = database.cryptoCurrencyDao().getCurrencies()
            emit(currenciesFromDatabase)
        }
    }

}