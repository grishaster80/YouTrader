package com.technopark.youtrader.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.model.CryptoCurrencyTransaction

@Dao
interface CryptoCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CryptoCurrency>)

    @Query("SELECT * from CryptoCurrency")
    suspend fun getCurrencies(): List<CryptoCurrency>

    @Update
    suspend fun updateCurrencies(currencies: List<CryptoCurrency>)

    @Delete
    suspend fun deleteCurrencies(currencies: List<CryptoCurrency>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyTransaction(currencies: CryptoCurrencyTransaction)

    @Query("SELECT * from CryptoCurrencyTransaction WHERE id = :id")
    suspend fun getCurrencyTransactions(id: String): CryptoCurrencyTransaction

    @Update
    suspend fun updateCurrencyTransaction(currencies: CryptoCurrencyTransaction)

    @Delete
    suspend fun deleteCurrencyTransaction(currencies: CryptoCurrencyTransaction)

    @Query("DELETE FROM CryptoCurrencyTransaction WHERE id = :id")
    suspend fun deleteCurrencyTransactions(id: String)
}
