package com.technopark.youtrader.base

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.technopark.youtrader.R
import com.technopark.youtrader.model.AuthState
import com.technopark.youtrader.ui.AppActivity

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private val navController by lazy { findNavController() }

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeNavigationEvent()
    }

    private fun observeNavigationEvent() {
        viewModel.events.observe(
            viewLifecycleOwner,
            EventObserver { navigationEvent ->
                handleNavigationEvent(navigationEvent)
            }
        )
    }

    private fun handleNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.ToDirection -> {
                Log.d(TAG, "Navigate to: ${event.direction}")
                navController.navigate(event.direction)
            }
            is NavigationEvent.Back -> {
                Log.d(TAG, "Navigate back")
                if (!navController.popBackStack()) activity?.finish()
            }
            is NavigationEvent.Up -> {
                Log.d(TAG, "Navigate up")
                navController.navigateUp()
            }
        }
    }

    private fun getSharedPrefs(): SharedPreferences? =
        (activity as? AppActivity)?.getSharedPreferences()

    fun getStringFromPrefs(key: String, defValue: String = ""): String {
        return getSharedPrefs()?.getString(key, defValue) ?: ""
    }

    fun setStringToPrefs(key: String, value: String = "") {
        getSharedPrefs()
            ?.edit()
            ?.apply { putString(key, value) }
            ?.apply()
    }

    fun setAuthState(authState: AuthState) {
        when (authState) {
            is AuthState.Authenticated -> {
                setStringToPrefs(getString(R.string.auth_state_key), authState.email)
            }
            is AuthState.PinActive -> {
                setStringToPrefs(getString(R.string.pin_code_key), authState.pin)
            }
            is AuthState.PinInactive -> deactivatePin()
            is AuthState.NotAuthenticated -> {
                setStringToPrefs(
                    getString(R.string.auth_state_key),
                    getString(R.string.value_is_not_defined)
                )
                deactivatePin()
            }
        }
    }

    private fun deactivatePin() {
        setStringToPrefs(
            getString(R.string.pin_code_key),
            getString(R.string.value_is_not_defined)
        )
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}
