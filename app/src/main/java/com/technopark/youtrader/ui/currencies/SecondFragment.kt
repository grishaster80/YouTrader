package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.SecondFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : BaseFragment(R.layout.second_fragment) {

    private val binding by viewBinding(SecondFragmentBinding::bind)

    override val viewModel: SecondViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            fragmentArgument.text = arguments?.getString("someString")

            buttonBackToAuthFragment.setOnClickListener {
                viewModel.navigateToFirstFragment()
            }

            binding.buttonToProfileFragment.setOnClickListener {
                viewModel.navigateToProfileFragment()
            }

            buttonUp.setOnClickListener {
                viewModel.goUp()
            }

            buttonBack.setOnClickListener {
                viewModel.goBack()
            }
        }
    }
}
