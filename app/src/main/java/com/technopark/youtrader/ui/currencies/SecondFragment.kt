package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.SecondFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : Fragment(R.layout.second_fragment) {

    private val binding by viewBinding(SecondFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            val navController = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
            navController?.navigate(SecondFragmentDirections.actionSecondFragmentToFirstFragment())
        }

        binding.button2.setOnClickListener {
            val navController = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
            navController?.navigate(SecondFragmentDirections.actionSecondFragmentToProfileFragment())
        }
    }
}