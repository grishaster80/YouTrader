package com.technopark.youtrader.ui

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
        R.id.authFragment,
        R.id.currenciesFragment,
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

            with(binding) {
                bottomNavView.visibility = if (destination.id in bottomNavViewFragmentIds
                ) {
                    toolbar.navigationIcon = null
                    View.VISIBLE
                } else {
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
                    View.GONE
                }
            }
        }
    }

    private fun navigateBack() {
        navController.popBackStack()
    }
}
