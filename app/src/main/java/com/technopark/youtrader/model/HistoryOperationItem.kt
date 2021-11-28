package com.technopark.youtrader.model

import android.view.View
import androidx.core.content.ContextCompat
import com.technopark.youtrader.R
import com.technopark.youtrader.database.transaction_entity.Transaction
import com.technopark.youtrader.databinding.HistoryOperationItemBinding
import com.technopark.youtrader.utils.timestampToFormatDate
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat
import java.util.*

class HistoryOperationItem(
    private val transaction: Transaction,
    private val sym : String
) : BindableItem<HistoryOperationItemBinding>() {

    override fun bind(viewBinding: HistoryOperationItemBinding, position: Int) {
        with(viewBinding) {


            if (transaction.timestamp != null)
                opDate.text = timestampToFormatDate(transaction.timestamp.toLong(),"dd MMM yyyy")
            if (transaction.amount > 0) {
                amountCurrency.text = "+".plus (transaction.amount.toString())
            } else {
                amountCurrency.text = "-".plus (transaction.amount.toString())
            }
            tickerInfo.text = sym
            price.text = "$".plus(transaction.price.toString())

            if (amountCurrency.text[0] == '-') {
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
