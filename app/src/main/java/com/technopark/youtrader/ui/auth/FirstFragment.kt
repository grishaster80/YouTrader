package com.technopark.youtrader.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.FirstFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FirstFragmentBinding? = null
    private val binding: FirstFragmentBinding
        get() = _binding!!

    private val viewModel: FirstViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FirstFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            val navHostFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
            val navController = navHostFragment.findNavController()
            navController.navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
        }

        viewModel.cryptoCurrencies.observe(viewLifecycleOwner, { currencies ->
            Log.d(TAG, "Received: ${currencies.first()}")
        })
        viewModel.getCryptoCurrencies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FirstFragmentTag"
    }
}