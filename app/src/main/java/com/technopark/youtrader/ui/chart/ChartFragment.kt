package com.technopark.youtrader.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.ChartFragmentBinding
import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.ui.currencies.CurrenciesFragment
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.invisible
import com.technopark.youtrader.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currencies_fragment.*

@AndroidEntryPoint
class ChartFragment : BaseFragment(R.layout.chart_fragment) {
    private val binding by viewBinding(ChartFragmentBinding::bind)

    override val viewModel: ChartViewModel by viewModels()

    private var lineChart: LineChart? = null
    private var scoreList = ArrayList<Score>()
    private var id: String? = null
    private var title: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString("id")
        with(binding) {
            lineChart = chart
            nameCryptocurrency.text = title
        }
        initLineChart()
        viewModel.updateCurrencyChartHistory(id ?: "bitcoin")
        viewModel.screenState.observe(
            viewLifecycleOwner,
            { screenState ->
                when (screenState) {
                    is Result.Success -> {
                        setDataToLineChart(screenState.data)
                        with(binding) {
                            progressBar.gone()
                            chart.visible()
                        }
                    }
                    is Result.Loading -> {
                        with(binding) {
                            progressBar.visible()
                            chart.invisible()
                        }
                    }
                    is Result.Error -> {
                        // TODO parse error
                        Toast.makeText(
                            requireContext(),
                            screenState.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        screenState.exception.message?.let { Log.d(ChartFragment.TAG, it) }
                        binding.progressBar.gone()
                    }
                }
            }
        )
    }

    private fun initLineChart() {

//        hide grid lines
        lineChart?.axisLeft?.setDrawGridLines(false)
        val xAxis: XAxis? = lineChart?.xAxis
        xAxis?.setDrawGridLines(false)
        xAxis?.setDrawAxisLine(false)

        // remove right y-axis
        lineChart?.axisRight?.isEnabled = false

        // remove legend
        lineChart?.legend?.isEnabled = false

        // remove description label
        lineChart?.description?.isEnabled = false

        // to draw label on xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = MyAxisFormatter(scoreList)
        xAxis?.setDrawLabels(true)
        xAxis?.granularity = 1f
    }

    private fun setDataToLineChart(chartElements: List<CurrencyChartElement>) {
        val entries: ArrayList<Entry> = ArrayList()
        scoreList.clear()
        for (i in chartElements) {
            scoreList.add(transformDateD1(i))
        }
        title = "1 $id  = " + scoreList.last().value.toString() + " $"
        binding.nameCryptocurrency.text = title

        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.value))
        }

        val lineDataSet = LineDataSet(entries, "")

        lineDataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = Color.BLACK
            this.setDrawValues(false)
            this.setDrawIcons(false)
            setDrawCircles(false)
        }

        val data = LineData(lineDataSet)
        lineChart?.data = data

        lineChart?.invalidate()
    }
    private fun transformDateD1(currencyChartElement: CurrencyChartElement): Score {
        val priceUsd = currencyChartElement.priceUsd.toFloat()
        val date = currencyChartElement.date.subSequence(2, 4).toString() + '.' +
            currencyChartElement.date.subSequence(5, 7).toString() + '.' +
            currencyChartElement.date.subSequence(8, 10).toString()

        return Score(date, priceUsd)
    }

    companion object {
        private const val TAG = "ChartFragment"
    }
}
