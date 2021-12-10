package com.technopark.youtrader.ui.pin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.PinFragmentBinding
import com.technopark.youtrader.ui.AppActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinAuthFragment : BaseFragment(R.layout.pin_fragment) {

    private val binding by viewBinding(PinFragmentBinding::bind)

    override val viewModel: PinAuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isPinSet()) {
            viewModel.navigateToCurrenciesFragment()
        }

        with(binding) {
            label.text = getFullNameFromPrefs()

            passcodeView.setPasscodeEntryListener { passcode ->
                if (passcode == getPinFromPrefs()) {
                    viewModel.navigateToCurrenciesFragment()
                } else {
                    passcodeView.clearText()
                    Toast.makeText(
                        activity,
                        getString(R.string.pin_is_incorrect),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            keyboard.setPasscodeView(passcodeView)
        }
    }

    private fun getFullNameFromPrefs(): String {
        return getStringFromPrefs(
            getString(R.string.profile_name_key),
            getString(R.string.value_is_not_defined)
        )
    }

    private fun getPinFromPrefs(): String {
        return getStringFromPrefs(
            getString(R.string.pin_code_key),
            getString(R.string.value_is_not_defined)
        )
    }

    private fun isPinSet(): Boolean = (activity as AppActivity).isPinSet()

    companion object {
        const val TAG = "PinAuthFragmentTag"
    }
}
