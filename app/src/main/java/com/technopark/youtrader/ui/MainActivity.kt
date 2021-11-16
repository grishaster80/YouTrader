package com.technopark.youtrader.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val bottomNavViewFragmentIds = listOf(
        R.id.currenciesFragment,
        R.id.portfolioFragment,
        R.id.profileFragment
    )

    private val withoutToolbarFragmentIds = listOf(
        R.id.authFragment,
        R.id.regFragment,
        R.id.currenciesFragment,
        R.id.portfolioFragment,
        R.id.profileFragment
    )

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        with(binding) {
            bottomNavView.setupWithNavController(navController)
            toolbar.setupWithNavController(navController)
            toolbar.setNavigationOnClickListener {
                navigateBack()
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavView.visibility = navViewVisibility(destination.id)
            binding.toolbar.navigationIcon = navigationIconId(destination.id)
        }
    }

    private fun navigateBack() {
        navController.popBackStack()
    }

    private fun navViewVisibility(destinationId: Int): Int {
        return if (destinationId in bottomNavViewFragmentIds)
            View.VISIBLE
        else
            View.GONE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun navigationIconId(destinationId: Int): Drawable? {
        return if (destinationId in withoutToolbarFragmentIds)
            null
        else
            getDrawable(R.drawable.ic_arrow_back)
    }
}
