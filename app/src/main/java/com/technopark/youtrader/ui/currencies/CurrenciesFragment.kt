package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.CurrenciesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : BaseFragment(R.layout.currencies_fragment) {

    private val binding by viewBinding(CurrenciesFragmentBinding::bind)

    override val viewModel: CurrenciesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.currenciesRecyclerView.adapter = CurrenciesRecyclerAdapter(
            arrayOf(CurrencyItem("BitCoin","66k"),
                    CurrencyItem("DogeCoin","133k"),
                CurrencyItem("DogeCoin","133k"),
                CurrencyItem("DogeasadassadasdasCoin","133k"),
                CurrencyItem("DogeCasdasdasdoin","133k"),
                CurrencyItem("DogeqweCoin","133k"),
                CurrencyItem("DogeqweCoin","133k"),
                CurrencyItem("DogwqeeCoin","133k"),
                CurrencyItem("DogeqweCoin","133k"),
                CurrencyItem("DogeCqweqwoin","133k"),
                CurrencyItem("DogeqweCoin","133k"),
                CurrencyItem("DogeCqweoin","133k"),
                CurrencyItem("DogeCqweoin","133k"),).toList()
        )
    }
}