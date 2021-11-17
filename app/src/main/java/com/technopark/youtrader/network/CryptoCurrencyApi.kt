package com.technopark.youtrader.network

import com.technopark.youtrader.model.CryptoCurrencyWrapper
import com.technopark.youtrader.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoCurrencyApi {

    @GET("assets/{currency}")
    suspend fun getCurrency(@Path("currency") currency: String): NetworkResponse<CryptoCurrencyWrapper>
    // TODO create custom Response wrapper
    @GET("assets")
    fun getValue(): Call<Response>
}
