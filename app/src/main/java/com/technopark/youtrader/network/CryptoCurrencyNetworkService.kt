package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrency
import javax.inject.Inject

class CryptoCurrencyNetworkService @Inject constructor(private val cryptoApi: CryptoCurrencyApi) {

    fun getCryptoCurrency(): List<CryptoCurrency> {
        val isInternetConnected = checkNetworkConnection()

        return if (isInternetConnected) {
            //TODO добавить обход 429 - "Превышено количество запросов"
            cryptoApi.getValue().execute().body()?.data
                ?: listOf(CryptoCurrency())
        } else {
            listOf()
        }
    }

    private fun checkNetworkConnection(): Boolean {
        return true
    }
}
