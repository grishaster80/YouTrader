package com.technopark.youtrader.ui.currencies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technopark.youtrader.R

class CurrenciesRecyclerAdapter(private val currenciesItems: List<CurrencyItem>) :
    RecyclerView.Adapter<CurrenciesRecyclerAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var currencyName: TextView? = null
        var currencyPrice: TextView? = null

        init {
            currencyName = itemView.findViewById(R.id.currency_name)
            currencyPrice = itemView.findViewById(R.id.currency_price)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currency_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.currencyName?.text = currenciesItems[position].name
        holder.currencyPrice?.text = currenciesItems[position].price
    }

    override fun getItemCount() = currenciesItems.size

}