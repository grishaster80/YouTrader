package com.technopark.youtrader.network

import android.util.Log
import com.technopark.youtrader.model.CryptoCurrencyExample
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun getCurrency() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val response = cryptoApi.getCurrency("bitcoin")
                when (response) {
                    is NetworkResponse.Success -> Log.d(TAG, response.body.data.name)
                    is NetworkResponse.ApiError ->
                        Log.d(TAG, "ApiError. message: ${response.error}, code: ${response.code}")
                    is NetworkResponse.NetworkError -> Log.d(TAG, "NetworkError")
                    is NetworkResponse.UnknownError -> Log.d(TAG, "UnknownError")
                }
            }
        }
    }

    private fun checkNetworkConnection(): Boolean {
        return true
    }

    companion object {
        private const val TAG = "CryptoCurrencyNetworkSe"
    }
}
