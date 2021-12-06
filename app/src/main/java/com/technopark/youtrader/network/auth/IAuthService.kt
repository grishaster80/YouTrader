package com.technopark.youtrader.network.auth

import kotlinx.coroutines.flow.Flow

interface IAuthService {
    suspend fun sighOut(): Flow<Boolean?>
    suspend fun updatePassword(password: String): Flow<Boolean?>
    suspend fun authenticate(email: String, password: String): Flow<CommonAuthResult?>
    suspend fun registerUser(email: String, password: String): Flow<CommonAuthResult?>
}
