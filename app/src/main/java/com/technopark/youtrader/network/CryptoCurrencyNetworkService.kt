package com.technopark.youtrader.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.technopark.youtrader.model.CryptoCurrencyExample
import com.technopark.youtrader.model.CryptoCurrencyWrapper
import com.technopark.youtrader.repository.CryptoCurrencyRepository
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
//            withContext(Dispatchers.IO) {
//                try {
//                    val response = cryptoApi.getCryptoCurrency("bitcoin")
////                    Log.d(TAG, response.data.getOrDefault("name", "some string"))
//                    Log.d(TAG, response.data.name)
//                } catch (e: Exception) {
//                    Log.d(TAG, "Error")
//                }
//            }

            withContext(Dispatchers.IO) {
                val response = cryptoApi.getCurrency("bitcoin")
                when (response) {
                    is NetworkResponse.Success -> Log.d(TAG, response.body.name)
//                    is NetworkResponse.ApiError -> Log.d(TAG, "ApiError ${response.body.message}")
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
