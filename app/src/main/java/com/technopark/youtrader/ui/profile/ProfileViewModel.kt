package com.technopark.youtrader.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.base.Event
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.network.auth.IAuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: IAuthService
) : BaseViewModel() {

    private val _updatePasswordState = MutableLiveData<Event<Result<Boolean>>>()
    val updatePasswordState: LiveData<Event<Result<Boolean>>> = _updatePasswordState

    private val _signOutState = MutableLiveData<Event<Result<Boolean>>>()
    val signOutState: LiveData<Event<Result<Boolean>>> = _signOutState

    fun navigateToPinRegFragment() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToPinRegFragment())
    }

    fun navigateToAuthFragment() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToAuthFragment())
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            _updatePasswordState.value = Event(Result.Loading)
            authService.updatePassword(password)
                .catch {
                    _updatePasswordState.value = Event(Result.Error(it))
                }.collect {
                    _updatePasswordState.value = Event(Result.Success(it ?: false))
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _signOutState.value = Event(Result.Loading)
            authService.sighOut()
                .catch {
                    _signOutState.value = Event(Result.Error(it))
                }
                .collect {
                    _signOutState.value = Event(Result.Success(it ?: false))
                }
        }
    }

    companion object {
        private const val TAG = "ProfileViewModelTag"
    }
}
