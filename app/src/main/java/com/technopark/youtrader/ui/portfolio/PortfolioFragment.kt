package com.technopark.youtrader.ui.portfolio

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.PortfolioFragmentBinding
import com.technopark.youtrader.model.PortfolioItem
import com.technopark.youtrader.utils.VerticalItemDecoration
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
            Log.d(TAG, "Go to the currency purchase history: ${item.portfolio.currencyName}")
            viewModel.navigateToInfoHistoryFragment()
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
        }

        adapter.setOnItemClickListener(onItemClickListener)

        viewModel.portfolioItems.observe(
            viewLifecycleOwner,
            { currencies ->
                adapter.update(currencies)
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
