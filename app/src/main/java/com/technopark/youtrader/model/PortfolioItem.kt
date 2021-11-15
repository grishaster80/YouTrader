package com.technopark.youtrader.model

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.PortfolioItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class PortfolioItem(
    val portfolio: Portfolio,
) : BindableItem<PortfolioItemBinding>() {

    override fun bind(viewBinding: PortfolioItemBinding, position: Int) {
        with(viewBinding) {
            currencyName.text = portfolio.currencyName
            currencyCount.text = portfolio.count
            price.text = portfolio.price
            changePrice.text = portfolio.priceChange
            setPortfolioPriceTextColor(changePrice)
        }
    }

    private fun setPortfolioPriceTextColor(changePrice: TextView) {
        if (changePrice.text[0] == '-') changePrice.setTextColor(
            ContextCompat.getColor(
                changePrice.context,
                R.color.red
            )
        )
        else changePrice.setTextColor(
            ContextCompat.getColor(
                changePrice.context,
                R.color.green
            )
        )
    }

    override fun getLayout(): Int = R.layout.portfolio_item

    override fun initializeViewBinding(view: View): PortfolioItemBinding =
        PortfolioItemBinding.bind(view)
}
