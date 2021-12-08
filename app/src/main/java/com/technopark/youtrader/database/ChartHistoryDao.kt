package com.technopark.youtrader.database

import androidx.room.*
import com.technopark.youtrader.model.Chart

@Dao
interface ChartHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChart(chart: Chart)

    @Query("SELECT * from Chart WHERE id = :id AND interval = :interval")
    suspend fun getCurrenciesByIdAndInterval(
        id: String?,
        interval: String?
    ): List<Chart>

    @Delete
    suspend fun deleteCharts(charts: List<Chart>)

    @Update
    suspend fun updateCharts(charts: List<Chart>)

    @Query("DELETE FROM Chart")
    suspend fun deleteAllCharts()
}
