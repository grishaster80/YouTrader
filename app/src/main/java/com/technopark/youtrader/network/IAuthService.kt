package com.technopark.youtrader.network

interface IAuthService {
    fun checkSignIn(email: String): Boolean
    fun signIn(email: String, password: String)
    fun sighUp(email: String, password: String)
    fun sighOut()
    fun updatePassword(password: String)
}
