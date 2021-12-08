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
import com.technopark.youtrader.utils.Constants.Companion.ARG_CURRENCY_ID
import com.technopark.youtrader.utils.Constants.Companion.MINUS_SYMBOL
import com.technopark.youtrader.utils.Constants.Companion.PERCENTAGE_PRECISION
import com.technopark.youtrader.utils.Constants.Companion.SIMPLE_PRECISION
import com.technopark.youtrader.utils.Constants.Companion.USD_SYMBOL
import com.technopark.youtrader.utils.VerticalItemDecoration
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.roundTo
import com.technopark.youtrader.utils.visible
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoCurrencyFragment : BaseFragment(R.layout.info_currency_fragment) {

    private val binding by viewBinding(InfoCurrencyFragmentBinding::bind)

    override val viewModel: InfoCurrencyViewModel by viewModels()

    private val adapter by lazy { GroupieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencyId = arguments?.getString(ARG_CURRENCY_ID)

        if (currencyId != null) {
            viewModel.updateCurrencyInformation(currencyId)
            viewModel.updateCurrencyTransactions(currencyId)
        }

        with(binding) {
            opHistoryRecyclerView.adapter = adapter
            opHistoryRecyclerView.addItemDecoration(
                VerticalItemDecoration(
                    resources.getDimension(R.dimen.indent_tiny).toInt()
                )
            )
        }

        viewModel.screenState.observe(
            viewLifecycleOwner,
            { screenState ->
                when (screenState) {
                    is Result.Success -> {
                        with(binding) {
                            progressBar.gone()

                            ticker.text = screenState.data.cryptoCurrency.symbol
                            total.text = roundTo(screenState.data.totalAmount)
                            price.text = USD_SYMBOL.plus(roundTo(screenState.data.totalPrice, SIMPLE_PRECISION))
                            absChange.text = roundTo(screenState.data.absChange, SIMPLE_PRECISION)
                            relativeChange.text = roundTo(screenState.data.relativeChange, PERCENTAGE_PRECISION).plus( "%")
                            if (relativeChange.text[0] == MINUS_SYMBOL) {
                                absChange.setTextColor(ContextCompat.getColor(price.context, R.color.red))
                                relativeChange.setTextColor(ContextCompat.getColor(relativeChange.context, R.color.red))
                            }
                            currencyNameInfo.text = screenState.data.cryptoCurrency.name

                            adapter.update(screenState.data.operationItemList)
                        }
                    }
                    is Result.Loading -> {
                        binding.progressBar.visible()
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            requireContext(),
                            screenState.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        screenState.exception.message?.let { Log.d(InfoCurrencyFragment.TAG, it) }
                        binding.progressBar.gone()
                    }
                }
            }
        )
    }

    companion object {
        private const val TAG = "InfoCurrencyFragment"
    }
}
