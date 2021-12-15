package com.technopark.youtrader.network.firebase

import android.net.Uri
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface IProfileFirebaseRepository {
    suspend fun setFullNameToFirebase(username: String)
    suspend fun setPasscodeToFirebase(passcode: String)
    suspend fun setPortraitUriToFirebase(uri: Uri)

    suspend fun getFullNameFromFirebase(): Flow<String>
    suspend fun getPasscodeFromFirebase(): Flow<String>
    suspend fun getPortraitUriFromFirebase(): Flow<Uri?>
    suspend fun getPortraitStorageReferenceFromFirebase(): Flow<StorageReference>

    fun addListener(listener: () -> Unit)
}