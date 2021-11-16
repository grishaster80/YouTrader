package com.technopark.youtrader.model

import android.view.View
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.CurrencyItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class CurrencyItem(
    val currency: CryptoCurrency,
) : BindableItem<CurrencyItemBinding>() {

    override fun bind(viewBinding: CurrencyItemBinding, position: Int) {
        with(viewBinding) {
            currencyName.text = currency.name
            currencyPrice.text = currency.priceUsd.toString()
        }
    }

    override fun getLayout(): Int = R.layout.currency_item

    override fun initializeViewBinding(view: View): CurrencyItemBinding =
        CurrencyItemBinding.bind(view)
}
