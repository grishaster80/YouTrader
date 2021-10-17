package com.technopark.youtrader.database

import androidx.room.Dao
import androidx.room.Update
import com.technopark.youtrader.model.ProfileDataExample

@Dao
interface ProfileDao {
    @Update
    suspend fun updateProfile(profile: ProfileDataExample)
}