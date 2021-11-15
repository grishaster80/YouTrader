package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.CurrenciesFragmentBinding
import com.technopark.youtrader.model.CurrencyItem
import com.technopark.youtrader.utils.VerticalItemDecoration
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currencies_fragment.*

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

        with(binding) {
            currenciesRecyclerView.adapter = adapter
            currenciesRecyclerView.addItemDecoration(
                VerticalItemDecoration(
                    resources.getDimension(R.dimen.indent_tiny).toInt()
                )
            )
        }

        adapter.setOnItemClickListener(onItemClickListener)

        viewModel.currencyItems.observe(
            viewLifecycleOwner,
            { currencies ->
                adapter.update(currencies)
            }
        )

        search.addTextChangedListener ( object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) {
                    viewModel.loadCurrencies()
                } else {
                    viewModel.updateCurrenciesByMatch(s.toString())
                }
                viewModel.currencyItems.observe(
                    viewLifecycleOwner,
                    { currencies ->
                        adapter.update(currencies)
                    }
                )
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }

    companion object {
        private const val TAG = "CurrenciesFragment"
    }
}
