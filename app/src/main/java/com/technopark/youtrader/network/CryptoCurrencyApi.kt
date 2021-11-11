package com.technopark.youtrader.network

import com.technopark.youtrader.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface CryptoCurrencyApi {

    // TODO create custom Response wrapper
    @GET("assets")
    fun getValue(): Call<Response>
}
