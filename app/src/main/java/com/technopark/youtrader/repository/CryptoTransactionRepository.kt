package com.technopark.youtrader.repository

import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.database.transaction_entity.LocalCryptoCurrencyTransaction
import com.technopark.youtrader.database.transaction_entity.TransactionUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoTransactionRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun getAllCurrencyTransaction(currencyId: String): Flow<List<TransactionUnit>> = flow {
        emit(database.cryptoTransactionDao().getAllTransactionByCurrency(currencyId))
    }.flowOn(Dispatchers.IO)

    fun getCurrency(currencyId: String): Flow<LocalCryptoCurrencyTransaction> = flow {
        emit(database.cryptoTransactionDao().getCurrency(currencyId))
    }.flowOn(Dispatchers.IO)

    fun getTotalAmount(currencyId: String): Flow<Double> = flow {
        emit(database.cryptoTransactionDao().getSumAmountByCurrencyId(currencyId))
    }.flowOn(Dispatchers.IO)

    fun getTotalPrice(currencyId: String): Flow<Double> = flow {
        emit(database.cryptoTransactionDao().getSumPriceByCurrencyId(currencyId))
    }.flowOn(Dispatchers.IO)

    private suspend fun insertCurrency(cur: LocalCryptoCurrencyTransaction) {
        database.cryptoTransactionDao().insertCurrency(cur)
    }

    suspend fun insertCurrency(id: String, name: String, sym: String) {
        insertCurrency(LocalCryptoCurrencyTransaction(id, sym, name))
    }

    private suspend fun insertTransaction(transactionUnit: TransactionUnit) {
        database.cryptoTransactionDao().insertTransaction(transactionUnit)
    }

    suspend fun insertTransaction(amount: Double, price: Double, currencyId: String) {
        insertTransaction(
            TransactionUnit(
                amount = amount,
                price = price,
                cryptoCurrencyId = currencyId,
                timestamp = System.currentTimeMillis().toString()
            )
        )
    }

    companion object {
        private const val TAG = "CryptoTransactionRepository"
    }
}
