package com.technopark.youtrader.database

import androidx.room.*
import com.technopark.youtrader.database.transaction_entity.LocalCryptoCurrencyTransaction
import com.technopark.youtrader.database.transaction_entity.TransactionUnit

@Dao
interface CryptoTransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrency(currency: LocalCryptoCurrencyTransaction)

    @Query("SELECT * from LocalCryptoCurrencyTransaction WHERE id = :id")
    suspend fun getCurrency(id: String): LocalCryptoCurrencyTransaction

    @Update
    suspend fun updateCurrency(currency: LocalCryptoCurrencyTransaction)

    @Delete
    suspend fun deleteCurrency(currency: LocalCryptoCurrencyTransaction)

    @Query("DELETE FROM LocalCryptoCurrencyTransaction WHERE id = :id")
    suspend fun deleteCurrencyById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(currency: TransactionUnit)

    @Query("SELECT * from currencyTransaction WHERE id = :id")
    suspend fun getCurrencyTransactions(id: String): TransactionUnit

    @Update
    suspend fun updateCurrencyTransaction(currency: TransactionUnit)

    @Delete
    suspend fun deleteCurrencyTransaction(currency: TransactionUnit)

    @Query("DELETE FROM currencyTransaction WHERE id = :id")
    suspend fun deleteCurrencyTransactions(id: String)

    @Query("SELECT * FROM currencyTransaction WHERE cryptoCurrencyId = :id ORDER BY timestamp DESC")
    suspend fun getAllTransactionByCurrency(id: String): List<TransactionUnit>

    @Query("SELECT SUM(amount) from currencyTransaction WHERE cryptoCurrencyId = :currencyId")
    suspend fun getSumAmountByCurrencyId(currencyId: String): Double

    @Query("SELECT SUM(price) from currencyTransaction WHERE cryptoCurrencyId = :currencyId")
    suspend fun getSumPriceByCurrencyId(currencyId: String): Double
}
