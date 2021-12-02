package com.technopark.youtrader.ui.pin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.PinFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinAuthFragment : BaseFragment(R.layout.pin_fragment) {

    private val binding by viewBinding(PinFragmentBinding::bind)

    override val viewModel: PinAuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Don't forget to call super.onViewCreated
        super.onViewCreated(view, savedInstanceState)

        if(!isPinEnabled()){
            viewModel.navigateToCurrenciesFragment()
        }

        with(binding) {
            label.text = getFullNameFromPrefs()

            passcodeView.setPasscodeEntryListener { passcode ->
                if(passcode == getPinFromPrefs()){
                    viewModel.navigateToCurrenciesFragment()
                }
                else{
                    passcodeView.clearText()
                    Toast.makeText(activity,"Введен неверный PIN", Toast.LENGTH_SHORT).show()
                }
            }

            keyboard.setPasscodeView(passcodeView)
        }
    }

    private fun getFullNameFromPrefs(): String {
        return getStringFromPrefs("full_name", "undefined")
    }

    private fun getPinFromPrefs(): String{
        return getStringFromPrefs("pin", "0000")
    }

    private fun isPinEnabled(): Boolean{
        return getStringFromPrefs("pin", "undefined") != "undefined"
    }

    companion object {
        const val TAG = "PinAuthFragmentTag"
    }
}
