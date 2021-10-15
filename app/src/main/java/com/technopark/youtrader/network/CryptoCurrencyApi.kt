package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrencyExample
import retrofit2.Response
import retrofit2.http.GET

interface CryptoCurrencyApi {

    @GET("value")
    suspend fun getValue(): Response<List<CryptoCurrencyExample>>
}