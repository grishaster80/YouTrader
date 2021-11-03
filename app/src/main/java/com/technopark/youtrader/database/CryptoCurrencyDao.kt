package com.technopark.youtrader.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.technopark.youtrader.model.CryptoCurrencyExample

@Dao
interface CryptoCurrencyDao {
    @Insert
    suspend fun insertCurrencies(currencies: List<CryptoCurrencyExample>)

    @Query("SELECT * from cryptocurrencyexample")
    suspend fun getCurrencies(): List<CryptoCurrencyExample>

    @Update
    suspend fun updateCurrencies(currencies: List<CryptoCurrencyExample>)

    @Delete
    suspend fun deleteCurrencies(currencies: List<CryptoCurrencyExample>)
}
