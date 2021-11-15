package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrencyWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoCurrencyApi {

    @GET("assets/{currency}")
    suspend fun getCurrency(@Path("currency") currency: String): NetworkResponse<CryptoCurrencyWrapper>
}
