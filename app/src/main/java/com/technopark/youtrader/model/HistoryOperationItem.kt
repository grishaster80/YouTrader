package com.technopark.youtrader.model

import android.view.View
import androidx.core.content.ContextCompat
import com.technopark.youtrader.R
import com.technopark.youtrader.databinding.HistoryOperationItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class HistoryOperationItem(
    private val history: HistoryOperation,
) : BindableItem<HistoryOperationItemBinding>() {

    override fun bind(viewBinding: HistoryOperationItemBinding, position: Int) {
        with(viewBinding) {
            opDate.text = history.date
            amountCurrency.text = history.amount
            price.text = history.price

            if (amountCurrency.text[0] == '-') {
                amountCurrency.setTextColor(
                    ContextCompat.getColor(amountCurrency.context, R.color.red)
                )
            }
        }
    }

    override fun getLayout(): Int = R.layout.history_operation_item

    override fun initializeViewBinding(view: View): HistoryOperationItemBinding =
        HistoryOperationItemBinding.bind(view)
}
