package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.SecondFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: SecondFragmentBinding? = null
    private val binding: SecondFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            val navHostFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
            val navController = navHostFragment.findNavController()
            navController.navigate(SecondFragmentDirections.actionSecondFragmentToFirstFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}