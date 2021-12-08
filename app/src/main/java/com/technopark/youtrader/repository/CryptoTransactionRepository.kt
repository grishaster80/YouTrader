package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.model.CryptoCurrencyTransaction
import com.technopark.youtrader.model.PortfolioCurrencyInfo
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CryptoTransactionRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun getAllCurrencyTransaction(currencyId: String): Flow<List<CryptoCurrencyTransaction>> = flow {
        emit(database.cryptoTransactionDao().getAllTransactionByCurrency(currencyId))
    }.flowOn(Dispatchers.IO)

    suspend fun getPortfolioCurrencies(): Flow<List<PortfolioCurrencyInfo>> = flow {
        emit(database.cryptoTransactionDao().getPortfolioCurrencies())
    }.flowOn(Dispatchers.IO)

    fun getCurrency(currencyId: String): Flow<CryptoCurrency> = flow {
        emit(database.cryptoTransactionDao().getCurrency(currencyId))
    }.flowOn(Dispatchers.IO)

    fun getTotalAmount(currencyId: String): Flow<Double> = flow {
        emit(database.cryptoTransactionDao().getSumAmountByCurrencyId(currencyId))
    }.flowOn(Dispatchers.IO)

    fun getTotalPrice(currencyId: String): Flow<Double> = flow {
        emit(database.cryptoTransactionDao().getSumPriceByCurrencyId(currencyId))
    }.flowOn(Dispatchers.IO)

    suspend fun insertTransaction(id: String, amount: Double, price: Double) {
        val timestamp = System.currentTimeMillis()
        val transaction = CryptoCurrencyTransaction(id, timestamp, amount, price)

        database.cryptoTransactionDao().insertTransaction(transaction)
    }

    suspend fun deleteAllCryptoCurrencyTransaction() {
        database.cryptoTransactionDao().deleteAllCryptoCurrencyTransaction()
    }

    companion object {
        private const val TAG = "CryptoTransactionRepository"
    }
}
