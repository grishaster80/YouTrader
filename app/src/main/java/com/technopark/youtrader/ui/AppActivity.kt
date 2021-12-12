package com.technopark.youtrader.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.ActivityMainBinding
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: AppViewModel by viewModels()

    private val bottomNavViewFragmentIds = listOf(
        R.id.currenciesFragment,
        R.id.portfolioFragment,
        R.id.profileFragment
    )

    private val withoutToolbarNavIconFragmentIds = listOf(
        R.id.authFragment,
        R.id.pinAuthFragment,
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

        handleStartScreen(viewModel.chooseStartScreen(isAuthSet(), isPinSet()))
        setupBottomNavigationView()
    }

    private fun isAuthSet(): Boolean {
        val valueIsNotDefined = getString(R.string.value_is_not_defined)
        return getSharedPreferences().getString(
            getString(R.string.auth_state_key),
            valueIsNotDefined
        ) != valueIsNotDefined
    }

    fun isPinSet(): Boolean {
        val valueIsNotDefined = getString(R.string.value_is_not_defined)
        return getSharedPreferences().getString(
            getString(R.string.pin_code_key),
            valueIsNotDefined
        ) != valueIsNotDefined
    }

    private fun handleStartScreen(startScreenId: Int) {
        val mainGraph = navController.navInflater.inflate(R.navigation.nav_graph).apply {
            startDestination = startScreenId
        }
        navController.graph = mainGraph
    }

    private fun setupBottomNavigationView() {
        with(binding) {
            bottomNavView.setupWithNavController(navController)
            toolbar.setupWithNavController(navController)
            toolbar.setNavigationOnClickListener {
                navigateBack()
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id in bottomNavViewFragmentIds) {
                    bottomNavView.visible()
                } else {
                    bottomNavView.gone()
                }
                toolbar.navigationIcon = navigationIconId(destination.id)
            }
        }
    }

    private fun navigateBack() {
        navController.popBackStack()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun navigationIconId(destinationId: Int): Drawable? {
        return if (destinationId in withoutToolbarNavIconFragmentIds)
            null
        else
            getDrawable(R.drawable.ic_arrow_back)
    }

    fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences(getString(R.string.shared_preference_name), 0)
    }
}
