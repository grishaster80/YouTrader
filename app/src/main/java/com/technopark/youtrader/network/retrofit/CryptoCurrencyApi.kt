package com.technopark.youtrader.network.retrofit

import com.technopark.youtrader.model.CurrenciesResponse
import retrofit2.http.GET

interface CryptoCurrencyApi {

    @GET("assets")
    suspend fun getCryptoCurrencies(): NetworkResponse<CurrenciesResponse>
}
