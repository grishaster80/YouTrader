package com.technopark.youtrader.database

import com.technopark.youtrader.model.CryptoCurrencyTransaction
import com.technopark.youtrader.model.PortfolioCurrencyInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technopark.youtrader.model.CryptoCurrency


@Dao
interface CryptoTransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrency(currency: CryptoCurrencyTransaction)

    @Query("SELECT * from CryptoCurrency WHERE id = :id")
    suspend fun getCurrency(id: String): CryptoCurrency

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

    @Query("SELECT id, SUM(amount) AS amount, SUM(price) AS price FROM CryptoCurrencyTransaction GROUP BY id")
    suspend fun getPortfolioCurrencies(): List<PortfolioCurrencyInfo>

    @Query("DELETE FROM CryptoCurrencyTransaction")
    suspend fun deleteAllCryptoCurrencyTransaction()

}
