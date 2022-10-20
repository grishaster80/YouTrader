package com.technopark.youtrader.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.StorageReference
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.base.Event
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.network.auth.IAuthService
import com.technopark.youtrader.network.firebase.IProfileFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: IAuthService,
    private val profileFirebaseRepository: IProfileFirebaseRepository
) : BaseViewModel() {

    private val _updatePasswordState = MutableLiveData<Event<Result<Boolean>>>()
    val updatePasswordState: LiveData<Event<Result<Boolean>>> = _updatePasswordState

    private val _signOutState = MutableLiveData<Event<Result<Boolean>>>()
    val signOutState: LiveData<Event<Result<Boolean>>> = _signOutState

    private val _fullNameState = MutableLiveData<Event<Result<String>>>()
    val fullNameState: LiveData<Event<Result<String>>> = _fullNameState

    private val _passcodeState = MutableLiveData<Event<Result<String>>>()
    val passcodeState: LiveData<Event<Result<String>>> = _passcodeState

    private val _portraitUriState = MutableLiveData<Event<Result<Uri>>>()
    val portraitUriState: LiveData<Event<Result<Uri>>> = _portraitUriState

    private val _portraitStorageReferenceState = MutableLiveData<Event<Result<StorageReference>>>()
    val portraitStorageReferenceState: LiveData<Event<Result<StorageReference>>> = _portraitStorageReferenceState


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

    fun updateFullNameFromFirebase(){
        viewModelScope.launch {
            profileFirebaseRepository.getFullNameFromFirebase().collect {
                _fullNameState.value = Event(Result.Success(it))
            }
        }
    }
    fun setFullNameToFirebase(fullName: String) {
        viewModelScope.launch {
            profileFirebaseRepository.setFullNameToFirebase(fullName)
        }
    }

    fun updatePasscodeFromFirebase() {
        viewModelScope.launch {
            profileFirebaseRepository.getPasscodeFromFirebase().collect{
                _passcodeState.value = Event(Result.Success(it))
            }
        }
    }

    fun updatePortraitUriFromFirebase() {
        viewModelScope.launch {
            profileFirebaseRepository.getPortraitUriFromFirebase().collect{
                if (it != null) {
                    _portraitUriState.value = Event(Result.Success(it))
                }
            }
        }
    }

    fun setPortraitToFirebase(uri: Uri) {
        viewModelScope.launch {
            profileFirebaseRepository.setPortraitUriToFirebase(uri)
        }
    }

    fun setPasscodeToFirebase(passcode: String) {
        viewModelScope.launch {
            profileFirebaseRepository.setPasscodeToFirebase(passcode)
        }
    }

    init {
        profileFirebaseRepository.addListener {
            updateFullNameFromFirebase()
            updatePasscodeFromFirebase()
            updatePortraitUriFromFirebase()
        }
    }



    companion object {
        private const val TAG = "ProfileViewModelTag"
    }
}
