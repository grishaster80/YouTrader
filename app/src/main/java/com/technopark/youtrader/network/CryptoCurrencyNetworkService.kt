package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrencyExample
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class CryptoCurrencyNetworkService @Inject constructor(private val cryptoApi: CryptoCurrencyApi) {

    fun getCryptoCurrency(): Response<List<CryptoCurrencyExample>> {
        val isInternetConnected = checkNetworkConnection()

        return if (isInternetConnected) {
            // TODO uncomment after stub deletion
//            cryptoApi.getValue()
            // TODO delete stub
            Response.success(listOf(CryptoCurrencyExample(123)))
        } else {
            Response.error(404,
                ResponseBody.create(MediaType.parse("application/json"), ""))
        }
    }

    private fun checkNetworkConnection(): Boolean {
        return true
    }
}