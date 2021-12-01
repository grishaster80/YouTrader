package com.technopark.youtrader.model

import android.view.View
import androidx.core.content.ContextCompat
import com.technopark.youtrader.R
import com.technopark.youtrader.database.transaction_entity.TransactionUnit
import com.technopark.youtrader.databinding.HistoryOperationItemBinding
import com.technopark.youtrader.utils.Constants.Companion.MINUS_SYMBOL
import com.technopark.youtrader.utils.Constants.Companion.PLUS_SYMBOL
import com.technopark.youtrader.utils.Constants.Companion.SIMPLE_DATE_FORMAT
import com.technopark.youtrader.utils.Constants.Companion.USD_SYMBOL
import com.technopark.youtrader.utils.roundTo
import com.technopark.youtrader.utils.timestampToFormatDate
import com.xwray.groupie.viewbinding.BindableItem

class HistoryOperationItem(
    private val transactionUnit: TransactionUnit,
    private val ticker: String
) : BindableItem<HistoryOperationItemBinding>() {

    override fun bind(viewBinding: HistoryOperationItemBinding, position: Int) {
        with(viewBinding) {

            if (transactionUnit.timestamp != null)
                opDate.text = timestampToFormatDate(transactionUnit.timestamp.toLong(), SIMPLE_DATE_FORMAT)
            if (transactionUnit.amount > 0) {
                amountCurrency.text = PLUS_SYMBOL.plus(roundTo(transactionUnit.amount))
            } else {
                amountCurrency.text = (transactionUnit.amount.toString())
            }
            tickerInfo.text = ticker
            price.text = USD_SYMBOL.plus(roundTo(transactionUnit.price))

            if (amountCurrency.text[0] == MINUS_SYMBOL) {
                amountCurrency.setTextColor(ContextCompat.getColor(amountCurrency.context, R.color.red))
            } else {
                amountCurrency.setTextColor(ContextCompat.getColor(amountCurrency.context, R.color.green))
            }
        }
    }

    override fun getLayout(): Int = R.layout.history_operation_item

    override fun initializeViewBinding(view: View): HistoryOperationItemBinding =
        HistoryOperationItemBinding.bind(view)
}
