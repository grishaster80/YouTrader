package com.technopark.youtrader.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.ProfileFragmentBinding
import com.technopark.youtrader.ui.currencies.SecondFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private val binding by viewBinding(ProfileFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener{
            val navController = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
            navController?.navigate(ProfileFragmentDirections.actionProfileFragmentToSecondFragment())
        }
    }


}