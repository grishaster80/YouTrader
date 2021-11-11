package com.technopark.youtrader.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.AuthFragmentBinding
import com.technopark.youtrader.model.CryptoCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.auth_fragment) {

    private val binding by viewBinding(AuthFragmentBinding::bind)

    override val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Don't forget to call super.onViewCreated
        super.onViewCreated(view, savedInstanceState)

        val email = "first.user@mail.com"
        val password = "qwerty"

        with(binding) {
            buttonSign.text = getString(R.string.sign_in)
            buttonToNextFragment.text = getString(R.string.to_sign_up)

            buttonSign.setOnClickListener {
                viewModel.signIn(email, password)
            }

            buttonToNextFragment.setOnClickListener {
                viewModel.navigateToRegFragment()
            }
        }

        lifecycleScope.launch {
            val res = viewModel.getCryptoCurrencies()
            showToast(res)
        }
    }

    suspend fun showToast(res: List<CryptoCurrency>) = withContext(Dispatchers.Main) {
        Log.d(TAG, "Received: ${res.first()}")
        Toast.makeText(
            requireContext(),
            "Received: ${res.first()}",
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        const val TAG = "FirstFragmentTag"
    }
}
