package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.network.retrofit.ApiErrorException
import com.technopark.youtrader.network.retrofit.CryptoCurrencyApi
import com.technopark.youtrader.network.retrofit.NetworkFailureException
import com.technopark.youtrader.network.retrofit.NetworkResponse
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CryptoCurrencyRepository @Inject constructor(
    private val cryptoApi: CryptoCurrencyApi,
    private val database: AppDatabase
) {
    suspend fun getCurrencies(): Flow<List<CryptoCurrency>> = flow {
        when (val currenciesFromNetwork = cryptoApi.getCryptoCurrencies()) {
            is NetworkResponse.Success -> {
                database.cryptoCurrencyDao().insertCurrencies(currenciesFromNetwork.value.data)
                emit(currenciesFromNetwork.value.data)
            }
            is NetworkResponse.ApiError -> {
                val currenciesFromDatabase = database.cryptoCurrencyDao().getCurrencies()
                if (currenciesFromDatabase.isNotEmpty()) {
                    emit(currenciesFromDatabase)
                } else {
                    throw ApiErrorException(currenciesFromNetwork.error, currenciesFromNetwork.code)
                }
            }
            is NetworkResponse.Failure -> {
                val currenciesFromDatabase = database.cryptoCurrencyDao().getCurrencies()
                if (currenciesFromDatabase.isNotEmpty()) {
                    emit(currenciesFromDatabase)
                } else {
                    throw currenciesFromNetwork.error ?: NetworkFailureException()
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val TAG = "CryptoCurrencyRepositor"
    }
}
