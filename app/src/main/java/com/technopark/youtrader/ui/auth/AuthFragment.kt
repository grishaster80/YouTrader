package com.technopark.youtrader.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.base.EventObserver
import com.technopark.youtrader.databinding.AuthFragmentBinding
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.auth_fragment) {

    private val binding by viewBinding(AuthFragmentBinding::bind)

    override val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailValue = "first.user@mail.com"
        val passwordValue = "qwerty"

        with(binding) {
            buttonSign.text = getString(R.string.sign_in)
            buttonToNextFragment.text = getString(R.string.to_sign_up)

            login.setText(emailValue)
            password.setText(passwordValue)

            buttonSign.setOnClickListener {
                viewModel.signIn(login.text.toString(), password.text.toString())
            }

            buttonToNextFragment.setOnClickListener {
                viewModel.navigateToRegFragment()
            }

            viewModel.authState.observe(
                viewLifecycleOwner,
                EventObserver { authState ->
                    when (authState) {
                        is Result.Loading -> {
                            progressBar.visible()
                        }
                        is Result.Success -> {
                            progressBar.gone()
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.auth_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.navigateToCurrenciesFragment()
                        }
                        is Result.Error -> {
                            progressBar.gone()
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    getString(
                                        R.string.auth_error,
                                        authState.exception.localizedMessage
                                    )
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        }
    }

    companion object {
        const val TAG = "AuthFragmentTag"
    }
}
