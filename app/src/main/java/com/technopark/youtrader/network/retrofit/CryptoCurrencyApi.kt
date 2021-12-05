package com.technopark.youtrader.network.retrofit

import com.technopark.youtrader.model.CurrenciesResponse
import com.technopark.youtrader.model.CurrencyChartResponse
import com.technopark.youtrader.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoCurrencyApi {

    @GET("assets")
    suspend fun getCryptoCurrencies(): NetworkResponse<CurrenciesResponse>

    @GET("assets/{Id}/history")
    suspend fun getCurrencyChartHistoryById(
        @Path("Id") id: String?,
        @Query("interval") interval: String?
    ): NetworkResponse<CurrencyChartResponse>

    @GET("assets/{Id}")
    suspend fun getCurrencyById(
        @Path("Id") id: String?,
    ): NetworkResponse<CurrencyResponse>

}
