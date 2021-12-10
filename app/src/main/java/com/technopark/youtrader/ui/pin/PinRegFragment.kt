package com.technopark.youtrader.ui.pin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.PinFragmentBinding
import com.technopark.youtrader.model.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinRegFragment : BaseFragment(R.layout.pin_fragment) {

    private val binding by viewBinding(PinFragmentBinding::bind)

    override val viewModel: PinRegViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pin = getString(R.string.value_is_not_defined)

        with(binding) {
            label.text = getString(R.string.pin_reg_logo)

            passcodeView.setPasscodeEntryListener { passcode ->
                when (pin) {
                    getString(R.string.value_is_not_defined) -> {
                        pin = passcode
                        passcodeView.clearText()
                        label.text = getString(R.string.repeat_pin_code)
                    }
                    passcode -> {
                        setAuthState(AuthState.PinActive(pin))
                        Toast.makeText(activity, getString(R.string.pin_set_successfully), Toast.LENGTH_SHORT)
                            .show()
                        viewModel.navigateToProfileFragment()
                    }
                    else -> {
                        passcodeView.clearText()
                        Toast.makeText(activity, getString(R.string.pin_mismatch), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            keyboard.setPasscodeView(passcodeView)
        }
    }

    companion object {
        const val TAG = "PinRegFragmentTag"
    }
}
