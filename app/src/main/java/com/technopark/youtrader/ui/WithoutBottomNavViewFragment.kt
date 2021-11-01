package com.technopark.youtrader.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.WithoutBottomNavViewFragmentBinding

class WithoutBottomNavViewFragment : Fragment(R.layout.without_bottom_nav_view_fragment) {

    private val binding by viewBinding(WithoutBottomNavViewFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
