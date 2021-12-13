package com.technopark.youtrader.network.firebase

import com.technopark.youtrader.model.CryptoCurrencyTransaction
import com.technopark.youtrader.model.PortfolioCurrencyInfo
import kotlinx.coroutines.flow.Flow

interface IFirebaseRepository {
    //suspend fun getUser(): Flow<User>
    suspend fun getCurrencyTransactionsById(currencyId: String): Flow<List<CryptoCurrencyTransaction>>
    suspend fun insertTransaction(currencyId: String, amount: Double, price: Double)
    suspend fun getPortfolioCurrencies(): Flow<List<PortfolioCurrencyInfo>>
    suspend fun getTotalAmount(currencyId: String): Flow<Double>
    suspend fun getTotalPrice(currencyId: String): Flow<Double>
    fun addListener(listener: () -> Unit)
}