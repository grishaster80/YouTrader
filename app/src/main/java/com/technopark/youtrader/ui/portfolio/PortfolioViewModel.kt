package com.technopark.youtrader.ui.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.Portfolio
import com.technopark.youtrader.model.PortfolioItem

class PortfolioViewModel  : BaseViewModel() {

    private val _portfolioItems: MutableLiveData<List<PortfolioItem>> =
        MutableLiveData(getPortfolioItems())
    val portfolioItems: LiveData<List<PortfolioItem>> = _portfolioItems

    fun navigateToWithoutBottomNavViewFragment() {
        navigateTo(PortfolioFragmentDirections.actionPortfolioFragmentToWithoutBottomNavViewFragment())
    }


    private fun getPortfolioItems(): List<PortfolioItem> {
        val portfolioItems = mutableListOf<PortfolioItem>()
        for (currency in getCurrencies()) {
            portfolioItems.add(PortfolioItem(currency))
        }
        return portfolioItems
    }

    private fun getCurrencies(): List<Portfolio> {
        return listOf(
            Portfolio("BitCoin","1 шт.", "64 193.2 \$","+1 705.90 (2,73%)"),
            Portfolio("Ethereum","1 шт.", "4 584.80 \$","+0.05038 (1,10%)"),
            Portfolio("Dogecoin","1 шт.", "0.26144 \$","-0.02714 (1,37%)"),
            Portfolio("Tether","2 шт.", "2.22 \$","-0.0038 (0,16%)"),
        )
    }
}