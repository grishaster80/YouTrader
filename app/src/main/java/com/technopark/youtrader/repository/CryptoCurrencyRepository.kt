package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.network.retrofit.ApiErrorException
import com.technopark.youtrader.network.retrofit.CryptoCurrencyApi
import com.technopark.youtrader.network.retrofit.NetworkFailureException
import com.technopark.youtrader.network.retrofit.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

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

    suspend fun getCurrencyChartHistoryById(id: String) :  Flow<List<CurrencyChartElement>> = flow {
        when(val currencyChartListFromNetwork = cryptoApi.getCurrencyChartHistoryById(id)){
            is NetworkResponse.Success -> {
                emit(currencyChartListFromNetwork.value.data)
            }
            is NetworkResponse.ApiError -> {
                throw ApiErrorException(currencyChartListFromNetwork.error, currencyChartListFromNetwork.code)
            }
            is NetworkResponse.Failure -> {
                throw currencyChartListFromNetwork.error ?: NetworkFailureException()
            }

        }
    }.flowOn(Dispatchers.IO)


    companion object {
        private const val TAG = "CryptoCurrencyRepositor"
    }
}
