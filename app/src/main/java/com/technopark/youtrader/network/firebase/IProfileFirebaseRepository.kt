package com.technopark.youtrader.network.firebase

import kotlinx.coroutines.flow.Flow

interface IProfileFirebaseRepository {
    suspend fun setFullNameFromFirebase(username: String)
    suspend fun setPasscodeFromFirebase(passcode: String)

    suspend fun getFullNameFromFirebase(): Flow<String>
    suspend fun getPasscodeFromFirebase(passcode: String) : Flow<String>

    fun addListener(listener: () -> Unit)
}