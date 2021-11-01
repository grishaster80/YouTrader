package com.technopark.youtrader.ui.currencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopark.youtrader.databinding.CurrencyItemBinding

class CurrenciesRecyclerAdapter(private val currenciesItems: List<CurrencyItem>) :
    RecyclerView.Adapter<CurrenciesRecyclerAdapter.MyViewHolder>(){

    class MyViewHolder(private val itemBinding : CurrencyItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bind(currencyItem: CurrencyItem) {
            with(itemBinding) {
                currencyName.text =  currencyItem.name
                currencyPrice.text = currencyItem.price
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currenciesItems[position])
    }

    override fun getItemCount() = currenciesItems.size

}