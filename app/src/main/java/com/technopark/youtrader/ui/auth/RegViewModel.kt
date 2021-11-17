package com.technopark.youtrader.ui.auth

import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.network.IAuthService
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject


@HiltViewModel
class RegViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository,
    private val authService: IAuthService
) : BaseViewModel() {



    fun signUp(email: String, password: String) = authService.sighUp(email, password)

    fun signIn(email: String, password: String) = authService.signIn(email, password)

    fun checkSignIn(email: String): Boolean = authService.checkSignIn(email)

    fun signOut() = authService.sighOut()

    fun navigateToAuthFragment() {
        val someString = "Random text"
        navigateTo(RegFragmentDirections.actionRegFragmentToAuthFragment(someString))
    }
}
