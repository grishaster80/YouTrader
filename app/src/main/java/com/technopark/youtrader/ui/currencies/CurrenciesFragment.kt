package com.technopark.youtrader.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopark.youtrader.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.currencies_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.currencies_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)
        val items : Array<CurrencyItem> = arrayOf(CurrencyItem("Bit","66k"),CurrencyItem("Doge","133k"))

        recyclerView?.adapter = CurrenciesRecyclerAdapter(items.toList())
    }
}