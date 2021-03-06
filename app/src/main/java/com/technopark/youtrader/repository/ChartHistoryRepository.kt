package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.model.Chart
import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.network.retrofit.ApiErrorException
import com.technopark.youtrader.network.retrofit.CryptoCurrencyApi
import com.technopark.youtrader.network.retrofit.NetworkFailureException
import com.technopark.youtrader.network.retrofit.NetworkResponse
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ChartHistoryRepository @Inject constructor(
    private val cryptoApi: CryptoCurrencyApi,
    private val database: AppDatabase
) {
    suspend fun getChartHistoryById(
        id: String?,
        interval: String?
    ): Flow<List<CurrencyChartElement>> = flow {
        val currencyChartListFromNetwork = cryptoApi.getCurrencyChartHistoryById(id, interval)
        when (currencyChartListFromNetwork) {
            is NetworkResponse.Success -> {
                if (id != null && interval != null) {
                    for (currencyChartElement in currencyChartListFromNetwork.value.data) {
                        database.chartHistoryDao().insertChart(
                            Chart(
                                id,
                                interval,
                                currencyChartListFromNetwork.value.timestamp,
                                currencyChartElement.priceUsd,
                                currencyChartElement.time,
                                currencyChartElement.date
                            )
                        )
                    }
                }
                emit(currencyChartListFromNetwork.value.data)
            }
            is NetworkResponse.ApiError -> {
                val currencyChartListFromDatabase = database.chartHistoryDao()
                    .getCurrenciesByIdAndInterval(id, interval).map {
                        chart ->
                        CurrencyChartElement(
                            chart.priceUsd,
                            chart.time,
                            chart.date
                        )
                    }
                if (currencyChartListFromDatabase.isNotEmpty()) {
                    emit(currencyChartListFromDatabase)
                } else throw ApiErrorException(
                    currencyChartListFromNetwork.error,
                    currencyChartListFromNetwork.code
                )
            }
            is NetworkResponse.Failure -> {
                val currencyChartListFromDatabase = database.chartHistoryDao()
                    .getCurrenciesByIdAndInterval(id, interval).map {
                        chart ->
                        CurrencyChartElement(
                            chart.priceUsd,
                            chart.time,
                            chart.date
                        )
                    }
                if (currencyChartListFromDatabase.isNotEmpty()) {
                    emit(currencyChartListFromDatabase)
                } else throw currencyChartListFromNetwork.error ?: NetworkFailureException()
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDatabaseChartHistoryById(
        id: String?,
        interval: String?
    ): Flow<List<CurrencyChartElement>> = flow {
        val currencyChartListFromDatabase = database.chartHistoryDao()
            .getCurrenciesByIdAndInterval(id, interval).map {
                chart ->
                CurrencyChartElement(
                    chart.priceUsd,
                    chart.time,
                    chart.date
                )
            }
        if (currencyChartListFromDatabase.isNotEmpty()) {
            emit(currencyChartListFromDatabase)
        } else {
            val chartListFromNetwork = getChartHistoryById(id, interval).first()
            if (chartListFromNetwork.isNotEmpty()) {
                emit(chartListFromNetwork)
            } else {
                throw NetworkFailureException()
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteAllCharts() {
        database.chartHistoryDao().deleteAllCharts()
    }

    companion object {
        private const val TAG = "ChartHistoryRepository"
    }
}
