package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrencyExample
import retrofit2.http.GET

interface CryptoCurrencyApi {

    // TODO create custom Response wrapper
    @GET("value")
    suspend fun getValue(): List<CryptoCurrencyExample>
}
