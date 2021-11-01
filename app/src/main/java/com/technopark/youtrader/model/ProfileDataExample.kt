package com.technopark.youtrader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiledataexample")
data class ProfileDataExample(@PrimaryKey val name: String)
