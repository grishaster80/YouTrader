package com.technopark.youtrader.repository

import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.network.retrofit.ApiErrorException
import com.technopark.youtrader.network.retrofit.CryptoCurrencyApi
import com.technopark.youtrader.network.retrofit.NetworkFailureException
import com.technopark.youtrader.network.retrofit.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChartHistoryRepository @Inject constructor(
    private val cryptoApi: CryptoCurrencyApi,
) {
    suspend fun getChartHistoryById(
        id: String?,
        interval: String?
    ): Flow<List<CurrencyChartElement>> = flow {
        val currencyChartListFromNetwork = cryptoApi.getCurrencyChartHistoryById(id, interval)
        when (currencyChartListFromNetwork) {
            is NetworkResponse.Success -> {
                emit(currencyChartListFromNetwork.value.data)
            }
            is NetworkResponse.ApiError -> {
                throw ApiErrorException(
                    currencyChartListFromNetwork.error,
                    currencyChartListFromNetwork.code
                )
            }
            is NetworkResponse.Failure -> {
                throw currencyChartListFromNetwork.error ?: NetworkFailureException()
            }
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val TAG = "ChartHistoryRepository"
    }
}
