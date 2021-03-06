package com.technopark.youtrader.ui.portfolio

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.PortfolioFragmentBinding
import com.technopark.youtrader.model.PortfolioItem
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.utils.Constants.Companion.PERCENTAGE_PRECISION
import com.technopark.youtrader.utils.Constants.Companion.SIMPLE_PRECISION
import com.technopark.youtrader.utils.VerticalItemDecoration
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.roundTo
import com.technopark.youtrader.utils.visible
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PortfolioFragment : BaseFragment(R.layout.portfolio_fragment) {

    private val binding by viewBinding(PortfolioFragmentBinding::bind)

    override val viewModel: PortfolioViewModel by viewModels()

    private val adapter by lazy { GroupieAdapter() }

    private val onItemClickListener = OnItemClickListener { item, view ->
        if (item is PortfolioItem) {
            Log.d(
                TAG,
                "Go to the currency purchase history: ${item.portfolioCurrencyInfo.id}"
            )
            viewModel.navigateToInfoHistoryFragment(item.portfolioCurrencyInfo.id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            portfolioRecyclerView.adapter = adapter
            portfolioRecyclerView.addItemDecoration(
                VerticalItemDecoration(
                    resources.getDimension(R.dimen.indent_tiny).toInt()
                )
            )
            setPortfolioTotalPriceTextColor(totalProfit)

            cleanPortfolio.setOnClickListener {
                viewModel.deleteAllCryptoCurrencyTransaction()
            }
        }

        adapter.setOnItemClickListener(onItemClickListener)

        viewModel.screenState.observe(
            viewLifecycleOwner,
            { screenState ->
                when (screenState) {
                    is Result.Success -> {
                        with(binding) {
                            progressBar.gone()
                            portfolioValue.text = String.format(
                                getString(
                                    R.string.value_with_currency_string_template
                                ),
                                roundTo(
                                    screenState.data.totalPrice, PERCENTAGE_PRECISION
                                )
                            )
                            totalProfit.text =
                                getString(
                                    R.string.profit_with_percentage,
                                    roundTo(screenState.data.absChange, SIMPLE_PRECISION),
                                    roundTo(
                                        screenState.data.relativeChange,
                                        PERCENTAGE_PRECISION
                                    )
                                )
                            setPortfolioTotalPriceTextColor(totalProfit)
                            portfolioRecyclerView.visible()
                        }
                        adapter.update(screenState.data.currencies)
                    }
                    is Result.Loading -> {
                        with(binding) {
                            progressBar.visible()
                            portfolioRecyclerView.gone()
                        }
                    }
                    is Result.Error -> {
                        // TODO parse error
                        Toast.makeText(
                            requireContext(),
                            screenState.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        screenState.exception.message?.let { Log.d(TAG, it) }
                        binding.progressBar.gone()
                    }
                }
            }
        )
    }

    private fun setPortfolioTotalPriceTextColor(totalProfit: TextView) {
        if (totalProfit.text[0] == '-') totalProfit.setTextColor(
            ContextCompat.getColor(
                totalProfit.context,
                R.color.red
            )
        )
        else totalProfit.setTextColor(
            ContextCompat.getColor(
                totalProfit.context,
                R.color.green
            )
        )
    }

    companion object {
        private const val TAG = "PortfolioFragment"
    }
}
