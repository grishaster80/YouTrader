package com.technopark.youtrader.database

import androidx.room.*
import com.technopark.youtrader.model.CryptoCurrencyTransaction

@Dao
interface CryptoTransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrency(currency: CryptoCurrencyTransaction)

    @Query("SELECT * from CryptoCurrencyTransaction WHERE id = :id")
    suspend fun getCurrency(id: String): CryptoCurrencyTransaction

    @Update
    suspend fun updateCurrency(currency: CryptoCurrencyTransaction)

    @Delete
    suspend fun deleteCurrency(currency: CryptoCurrencyTransaction)

    @Query("DELETE FROM CryptoCurrencyTransaction WHERE id = :id")
    suspend fun deleteCurrencyById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(currency: CryptoCurrencyTransaction)

    @Query("SELECT * from CryptoCurrencyTransaction WHERE id = :id")
    suspend fun getCurrencyTransactions(id: String): CryptoCurrencyTransaction

    @Update
    suspend fun updateCurrencyTransaction(currency: CryptoCurrencyTransaction)

    @Delete
    suspend fun deleteCurrencyTransaction(currency: CryptoCurrencyTransaction)

    @Query("DELETE FROM CryptoCurrencyTransaction WHERE id = :id")
    suspend fun deleteCurrencyTransactions(id: String)

    @Query("SELECT * FROM CryptoCurrencyTransaction WHERE id = :id ORDER BY timestamp DESC")
    suspend fun getAllTransactionByCurrency(id: String): List<CryptoCurrencyTransaction>

    @Query("SELECT SUM(amount) from CryptoCurrencyTransaction WHERE id = :currencyId")
    suspend fun getSumAmountByCurrencyId(currencyId: String): Double

    @Query("SELECT SUM(price) from CryptoCurrencyTransaction WHERE id = :currencyId")
    suspend fun getSumPriceByCurrencyId(currencyId: String): Double
}
