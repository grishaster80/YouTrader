package com.technopark.youtrader.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technopark.youtrader.model.CryptoCurrency

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
}
