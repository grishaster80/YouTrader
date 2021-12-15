package com.technopark.youtrader.network.firebase

import kotlinx.coroutines.flow.Flow

interface IProfileFirebaseRepository {
    suspend fun setFullNameToFirebase(username: String)
    suspend fun setPasscodeToFirebase(passcode: String)

    suspend fun getFullNameFromFirebase(): Flow<String>
    suspend fun getPasscodeFromFirebase(): Flow<String>

    fun addListener(listener: () -> Unit)
}