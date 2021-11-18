package com.technopark.youtrader.network

import com.technopark.youtrader.model.CurrenciesResponse
import javax.inject.Inject

class CryptoCurrencyNetworkService @Inject constructor(private val cryptoApi: CryptoCurrencyApi) {

    suspend fun getCryptoCurrency(): NetworkResponse<CurrenciesResponse> {
        return cryptoApi.getCryptoCurrencies()
    }

    companion object {
        private const val TAG = "CryptoCurrencyNetworkSe"
    }
}
