package com.technopark.youtrader.network.auth

import kotlinx.coroutines.flow.Flow

interface IAuthService {
    fun sighOut()
    fun updatePassword(password: String)
    suspend fun authenticate(email: String, password: String): Flow<CommonAuthResult?>
    suspend fun registerUser(email: String, password: String): Flow<CommonAuthResult?>
}
