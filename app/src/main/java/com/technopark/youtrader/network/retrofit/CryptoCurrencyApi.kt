package com.technopark.youtrader.network.retrofit

import com.technopark.youtrader.model.CurrenciesResponse
import com.technopark.youtrader.model.CurrencyChartResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoCurrencyApi {

    @GET("assets")
    suspend fun getCryptoCurrencies(): NetworkResponse<CurrenciesResponse>

    @GET("assets/{Id}/history?interval=d1")
    suspend fun getCurrencyChartHistoryById(@Path("Id") id: String):
        NetworkResponse<CurrencyChartResponse>
}
