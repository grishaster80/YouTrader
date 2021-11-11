package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrencyExample
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CryptoCurrencyNetworkService @Inject constructor(private val cryptoApi: CryptoCurrencyApi) {

    fun getCryptoCurrency(): List<CryptoCurrencyExample> {
        val isInternetConnected = checkNetworkConnection()

        return if (isInternetConnected) {
            cryptoApi.getValue().execute().body()?.data
                ?: listOf(CryptoCurrencyExample())
        } else {
            listOf()
        }
    }

    private fun checkNetworkConnection(): Boolean {
        return true
    }
}
