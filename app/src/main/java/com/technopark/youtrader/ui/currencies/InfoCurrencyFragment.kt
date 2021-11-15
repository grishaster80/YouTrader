package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.InfoCurrencyFragmentBinding
import com.technopark.youtrader.utils.VerticalItemDecoration
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.info_currency_fragment.*

@AndroidEntryPoint
class InfoCurrencyFragment : BaseFragment(R.layout.info_currency_fragment) {

    private val binding by viewBinding(InfoCurrencyFragmentBinding::bind)

    override val viewModel: InfoCurrencyViewModel by viewModels()

    private val adapter by lazy { GroupieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            opHistoryRecyclerView.adapter = adapter
            opHistoryRecyclerView.addItemDecoration(
                VerticalItemDecoration(
                    resources.getDimension(R.dimen.indent_tiny).toInt()
                )
            )
            ticker.text = "BTC"
            price.text = "$87741"
            absChange.text = "+$54"
            relativeChange.text = "+0.12%"
            if (price.text[0] == '-') {
                absChange.setTextColor(ContextCompat.getColor(price.context, R.color.red))
                relativeChange.setTextColor(ContextCompat.getColor(relativeChange.context, R.color.red))
            }
        }

        viewModel.historyOperationItems.observe(
            viewLifecycleOwner,
            { history ->
                adapter.update(history)
            }
        )
    }

    companion object {
        private const val TAG = "InfoCurrencyFragment"
    }
}
