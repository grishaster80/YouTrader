package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrencyExample
import javax.inject.Inject

class CryptoCurrencyNetworkService @Inject constructor(private val cryptoApi: CryptoCurrencyApi) {

    fun getCryptoCurrency(): List<CryptoCurrencyExample> {
        val isInternetConnected = checkNetworkConnection()

        return if (isInternetConnected) {
            // TODO uncomment after stub deletion
//            cryptoApi.getValue()
            // TODO delete stub
            listOf(CryptoCurrencyExample(123))
        } else {
            listOf()
        }
    }

    private fun checkNetworkConnection(): Boolean {
        return true
    }
}
