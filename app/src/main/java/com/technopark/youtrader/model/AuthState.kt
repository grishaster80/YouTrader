package com.technopark.youtrader.model

sealed class AuthState {
    data class Authenticated(val email: String) : AuthState()
    data class PinActive(val pin: String) : AuthState()
    object PinInactive : AuthState()
    object NotAuthenticated : AuthState()
}
