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
import com.technopark.youtrader.model.AuthState
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegFragment : BaseFragment(R.layout.auth_fragment) {

    private val binding by viewBinding(AuthFragmentBinding::bind)

    override val viewModel: RegViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonSign.text = getString(R.string.sign_up)
            buttonToNextFragment.text = getString(R.string.to_sign_in)
            anotherAuthFragment.setText(R.string.have_account_already)

            buttonSign.setOnClickListener {
                viewModel.signUp(login.text.toString(), password.text.toString())
            }

            buttonToNextFragment.setOnClickListener {
                viewModel.navigateToAuthFragment()
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
                                getString(R.string.registration_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            setAuthState(AuthState.Authenticated(login.text.toString()))
                            viewModel.navigateToCurrenciesFragment()
                        }
                        is Result.Error -> {
                            progressBar.gone()
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.registration_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        }
    }

    companion object {
        const val TAG = "RegFragmentTag"
    }
}
