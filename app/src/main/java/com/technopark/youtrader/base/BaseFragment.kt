package com.technopark.youtrader.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

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

    companion object {
        private const val TAG = "BaseFragment"
    }
}
