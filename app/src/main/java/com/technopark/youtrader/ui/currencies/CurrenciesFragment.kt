package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.CurrenciesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.currencies_fragment) {

    private val binding by viewBinding(CurrenciesFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.currenciesRecyclerView.adapter = CurrenciesRecyclerAdapter(
            arrayOf(CurrencyItem("BitCoin","66k"),
                    CurrencyItem("DogeCoin","133k")).toList()
        )
    }
}