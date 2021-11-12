package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrency
import javax.inject.Inject

class CryptoCurrencyNetworkService @Inject constructor(private val cryptoApi: CryptoCurrencyApi) {

    fun getCryptoCurrency(): List<CryptoCurrency> {
        val isInternetConnected = checkNetworkConnection()

        return if (isInternetConnected) {
            var resp = cryptoApi.getValue().execute().body()
            while(resp == null){
                resp = cryptoApi.getValue().execute().body()
            }
            resp.data
        } else {
            listOf()
        }
    }

    private fun checkNetworkConnection(): Boolean {
        return true
    }
}
