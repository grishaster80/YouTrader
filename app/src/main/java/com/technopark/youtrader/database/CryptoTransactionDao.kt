package com.technopark.youtrader.database

import androidx.room.*
import com.technopark.youtrader.database.transaction_entity.LocalCryptoCurrencyTransaction
import com.technopark.youtrader.database.transaction_entity.Transaction

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
    suspend fun deleteCurrency(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(currency: Transaction)

    @Query("SELECT * from currencyTransaction WHERE id = :id")
    suspend fun getCurrencyTransactions(id: String): Transaction

    @Update
    suspend fun updateCurrencyTransaction(currency: Transaction)

    @Delete
    suspend fun deleteCurrencyTransaction(currency: Transaction)

    @Query("DELETE FROM currencyTransaction WHERE id = :id")
    suspend fun deleteCurrencyTransactions(id: String)

    @Query("SELECT * FROM currencyTransaction WHERE cryptoCurrencyId = :id ORDER BY timestamp DESC")
    suspend fun getAllTransactionByCurrency(id: String) : List<Transaction>

}