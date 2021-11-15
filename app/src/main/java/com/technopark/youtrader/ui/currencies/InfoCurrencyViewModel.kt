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
            HistoryOperation("21 янв. 2021", "0,001", "$452300"),
            HistoryOperation("20 янв. 2021", "0,0002", "$65"),
            HistoryOperation("17 янв. 2021", "-0,0002184", "$4523"),
            HistoryOperation("15 мар. 2020", "0,0004", "$45"),
            HistoryOperation("21 дек. 2021", "-0,0000002184", "$4000523")

        )
    }

    companion object {
        private const val TAG = "InfoCurrencyViewModel"
    }
}
