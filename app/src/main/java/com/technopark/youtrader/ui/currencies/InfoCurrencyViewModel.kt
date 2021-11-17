package com.technopark.youtrader.ui.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.HistoryOperation
import com.technopark.youtrader.model.HistoryOperationItem

class InfoCurrencyViewModel : BaseViewModel() {

    private val _historyOperationItems: MutableLiveData<List<HistoryOperationItem>> =
        MutableLiveData(getHistoryOperationItems())
    val historyOperationItems: LiveData<List<HistoryOperationItem>> = _historyOperationItems

    private fun getHistoryOperationItems(): List<HistoryOperationItem> {
        val historyItems = mutableListOf<HistoryOperationItem>()
        for (history in getHistoryOperation()) {
            historyItems.add(HistoryOperationItem(history))
        }
        return historyItems
    }

    private fun getHistoryOperation(): List<HistoryOperation> {
        return listOf(
            HistoryOperation("17 ноя. 2021", "0.022", "$1330.2"),
            HistoryOperation("15 ноя. 2021", "0.1", "$6043.2"),
            HistoryOperation("28 окт. 2021", "-0.000832", "$50.300"),
            HistoryOperation("1 сен. 2020", "0,0000004", "$45"),
            HistoryOperation("12 дек. 2020", "-0,0002184", "$4523")

        )
    }

    companion object {
        private const val TAG = "InfoCurrencyViewModel"
    }
}
