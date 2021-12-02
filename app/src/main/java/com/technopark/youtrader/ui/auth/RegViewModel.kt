package com.technopark.youtrader.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.base.Event
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.network.auth.IAuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class RegViewModel @Inject constructor(
    private val authService: IAuthService
) : BaseViewModel() {
    private val _authState = MutableLiveData<Event<Result<String?>>>()
    val authState: LiveData<Event<Result<String?>>> = _authState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Event(Result.Loading)
            authService.registerUser(email, password)
                .catch {
                    _authState.value = Event(Result.Error(it))
                    Log.d(TAG, it.message.toString())
                }.collect {
                    _authState.value = Event(Result.Success(it?.userInfo))
                    Log.d(TAG, it?.userInfo.toString())
                }
        }
    }

    fun navigateToCurrenciesFragment() {
        navigateTo(RegFragmentDirections.actionRegFragmentToCurrenciesFragment())
    }

    fun navigateToAuthFragment() {
        navigateTo(RegFragmentDirections.actionRegFragmentToAuthFragment())
    }

    companion object {
        private const val TAG = "RegViewModelTag"
    }
}
