package com.technopark.youtrader.network

import com.technopark.youtrader.network.auth.CommonAuthResult
import kotlinx.coroutines.flow.Flow

interface IAuthService {
    fun checkSignIn(email: String): Boolean
    fun signIn(email: String, password: String)
    fun sighUp(email: String, password: String)
    fun sighOut()
    fun updatePassword(password: String)
}
