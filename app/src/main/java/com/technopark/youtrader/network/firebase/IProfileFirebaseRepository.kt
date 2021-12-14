package com.technopark.youtrader.network.firebase

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface IProfileFirebaseRepository {
    suspend fun setFullNameToFirebase(username: String)
    suspend fun setPasscodeToFirebase(passcode: String)

    suspend fun getFullNameFromFirebase(): Flow<String>
    suspend fun getPasscodeFromFirebase(): Flow<String>
    suspend fun getAvatarFromFirebase(): Flow<Uri?>

    fun addListener(listener: () -> Unit)
}