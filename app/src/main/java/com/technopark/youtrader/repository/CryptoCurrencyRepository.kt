package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.network.CryptoCurrencyNetworkService
import com.technopark.youtrader.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CryptoCurrencyRepository @Inject constructor(
    private val networkService: CryptoCurrencyNetworkService,
    private val database: AppDatabase
) {
    suspend fun getCurrencies(): NetworkResponse<Any> = withContext(Dispatchers.IO) {
        val currenciesFromNetwork = networkService.getCryptoCurrency()
        if (currenciesFromNetwork is NetworkResponse.Success) {
            // TODO cash in database
            return@withContext NetworkResponse.Success(currenciesFromNetwork.body.data)
        } else {
            val currenciesFromDatabase = database.cryptoCurrencyDao().getCurrencies()
            if (currenciesFromDatabase.isEmpty() &&
                (
                    currenciesFromNetwork is NetworkResponse.NetworkError ||
                        currenciesFromNetwork is NetworkResponse.ApiError ||
                        currenciesFromNetwork is NetworkResponse.UnknownError
                    )
            ) {
                return@withContext currenciesFromNetwork
            }
            return@withContext NetworkResponse.Success(currenciesFromDatabase)
        }
    }

    companion object {
        private const val TAG = "CryptoCurrencyRepositor"
    }
}
