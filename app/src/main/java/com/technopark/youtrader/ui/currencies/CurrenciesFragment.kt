package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.CurrenciesFragmentBinding
import com.technopark.youtrader.model.CurrencyItem
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : BaseFragment(R.layout.currencies_fragment) {

    private val binding by viewBinding(CurrenciesFragmentBinding::bind)
    override val viewModel: CurrenciesViewModel by viewModels()
    private val adapter by lazy { GroupieAdapter() }

    private val onItemClickListener = OnItemClickListener { item, view ->
        if (item is CurrencyItem) {
            Log.d(TAG, "Go to currency: ${item.currency.name}")
            viewModel.navigateToWithoutBottomNavViewFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.currenciesRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

        viewModel.currencyItems.observe(
            viewLifecycleOwner,
            { currencies ->
                adapter.update(currencies)
            }
        )
    }

    companion object {
        private const val TAG = "CurrenciesFragment"
    }
}
