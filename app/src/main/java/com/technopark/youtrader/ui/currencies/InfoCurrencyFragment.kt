package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.InfoCurrencyFragmentBinding
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.utils.VerticalItemDecoration
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.visible
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currencies_fragment.*
import kotlinx.android.synthetic.main.info_currency_fragment.*
import kotlinx.android.synthetic.main.portfolio_fragment.*

@AndroidEntryPoint
class InfoCurrencyFragment : BaseFragment(R.layout.info_currency_fragment) {

    private val binding by viewBinding(InfoCurrencyFragmentBinding::bind)

    override val viewModel: InfoCurrencyViewModel by viewModels()

    private val adapter by lazy { GroupieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencyId  = arguments?.getString("currencyId")

        if (currencyId != null) {
            viewModel.updateCurrency(currencyId)
            viewModel.updateCurrencyTransactions(currencyId)
        };

        with(binding) {
            opHistoryRecyclerView.adapter = adapter
            opHistoryRecyclerView.addItemDecoration(
                VerticalItemDecoration(
                    resources.getDimension(R.dimen.indent_tiny).toInt()
                )
            )
            currencyNameInfo.text = "Bitcoin"
            total.text = "0.22222"
            ticker.text = "BTC"
            price.text = "$87741"
            absChange.text = "+$54"
            relativeChange.text = "+(0.12%)"
            if (price.text[0] == '-') {
                absChange.setTextColor(ContextCompat.getColor(price.context, R.color.red))
                relativeChange.setTextColor(ContextCompat.getColor(relativeChange.context, R.color.red))
            }
        }
        viewModel.currency.observe(
            viewLifecycleOwner,
            {
                currency ->
                    with(binding) {
                        ticker.text = currency.symbol

                    }
            }
        )
        viewModel.screenState.observe(
            viewLifecycleOwner,
            { screenState ->
                when (screenState) {
                    is Result.Success -> {
                        binding.progressBar2.gone()
                        adapter.update(screenState.data)
                    }
                    is Result.Loading -> {
                        with(binding) {
                            progressBar2.visible()
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            requireContext(),
                            screenState.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        screenState.exception.message?.let { Log.d(InfoCurrencyFragment.TAG, it) }
                        binding.progressBar2.gone()
                    }
                }
            }
        )
    }

    companion object {
        private const val TAG = "InfoCurrencyFragment"
    }
}
