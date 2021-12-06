package com.technopark.youtrader.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.ChartFragmentBinding
import com.technopark.youtrader.model.CurrencyChartElement
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.utils.gone
import com.technopark.youtrader.utils.invisible
import com.technopark.youtrader.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment(R.layout.chart_fragment) {
    private val binding by viewBinding(ChartFragmentBinding::bind)

    override val viewModel: ChartViewModel by viewModels()

    private var lineChart: LineChart? = null
    private var scoreList = ArrayList<Score>()
    private var id: String? = null
    private var title: String? = null
    private var interval: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString("id")
        with(binding) {
            lineChart = chart
            nameCryptocurrency.text = title

            buttonBuy.setOnClickListener{
                val amountStr = editCountCurrencies.text.toString()
                if(amountStr in badInputValues){
                    Toast.makeText(activity, R.string.invalid_input, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val amount = amountStr.toDouble()
                //TODO убрать, когда будем получать цену из API
                val price = scoreList.last().value.toDouble() * amount
                id?.let{ viewModel.buyCryptoCurrency(it, amount, price) }
                Toast.makeText(activity, getString(R.string.buy_successful) + " $amount $price" , Toast.LENGTH_LONG).show()
            }
        }
        interval = intervalYear
        initLineChart()
        viewModel.updateCurrencyChartHistory(id, interval)
        updateRadioButton()
        viewModel.screenState.observe(
            viewLifecycleOwner,
            { screenState ->
                when (screenState) {
                    is Result.Success -> {
                        setDataToLineChart(screenState.data)
                        with(binding) {
                            buttonBuy.isEnabled = true
                            progressBar.gone()
                            chart.visible()
                        }
                    }
                    is Result.Loading -> {
                        with(binding) {
                            buttonBuy.isEnabled = false
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
                        screenState.exception.message?.let { Log.d(TAG, it) }
                        binding.buttonBuy.isEnabled = true
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
        xAxis?.granularity = granularity
        xAxis?.labelRotationAngle = xAxisRotationAngle

        lineChart?.setXAxisRenderer(
            CustomXAxisRenderer(
                lineChart?.viewPortHandler,
                lineChart?.xAxis,
                lineChart?.getTransformer(YAxis.AxisDependency.LEFT)
            )
        )
    }

    private fun setDataToLineChart(chartElements: List<CurrencyChartElement>) {
        val entries: ArrayList<Entry> = ArrayList()
        scoreList.clear()
        for (i in chartElements) {
            scoreList.add(currencyChartElementToScore(i))
        }
        title = constructionTitle()
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

    private fun currencyChartElementToScore(currencyChartElement: CurrencyChartElement): Score {
        val priceUsd = currencyChartElement.priceUsd.toFloat()
        val date = convertFormatDate(currencyChartElement.date)

        return Score(date, priceUsd)
    }

    private fun convertFormatDate(date: String): String {
        /* yyyy-mm-ddTHH:MM:SS.mmmZ
           ->
           dd-mm
           HH:MM
        */
        val month = date.subSequence(5, 7)
        val day = date.subSequence(8, 10)
        val hour = date.subSequence(11, 13)
        val minute = date.subSequence(14, 16)

        return "$day-$month\n$hour:$minute"
    }

    private fun constructionTitle(): String {
        return "1 $id  = " + scoreList.last().value.toString() + " $"
    }
    private fun updateRadioButton() {
        val radioGroup = binding.radioGroupInterval
        val radioButtonDay = binding.radioButtonDay
        val radioButtonWeek = binding.radioButtonWeek
        val radioButtonMonth = binding.radioButtonMonth
        val radioButtonYear = binding.radioButtonYear
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (radioButtonDay.id == checkedId) {
                interval = intervalDay
                changeRadioButtonColor(radioButtonDay, R.color.gray)
            } else {
                changeRadioButtonColor(radioButtonDay, R.color.white)
            }

            if (radioButtonWeek.id == checkedId) {
                interval = intervalWeek
                changeRadioButtonColor(radioButtonWeek, R.color.gray)
            } else {
                changeRadioButtonColor(radioButtonWeek, R.color.white)
            }

            if (radioButtonMonth.id == checkedId) {
                interval = intervalMonth
                changeRadioButtonColor(radioButtonMonth, R.color.gray)
            } else {
                changeRadioButtonColor(radioButtonMonth, R.color.white)
            }
            if (radioButtonYear.id == checkedId) {
                interval = intervalYear
                changeRadioButtonColor(radioButtonYear, R.color.gray)
            } else {
                changeRadioButtonColor(radioButtonYear, R.color.white)
            }


            initLineChart()
            viewModel.updateCurrencyChartHistory(id, interval)
        }

    }

    private fun changeRadioButtonColor(button: AppCompatRadioButton, color: Int) {
        button.setBackgroundColor(
            ContextCompat.getColor(
                button.context,
                color
            )
        )
    }

    companion object {
        private const val TAG = "ChartFragment"
        private const val xAxisRotationAngle = -30f
        private const val granularity = 1f
        private const val intervalDay = "m1"
        private const val intervalWeek = "m15"
        private const val intervalMonth = "h1"
        private const val intervalYear = "d1"
        private val badInputValues = listOf("","0","0.")
    }
}
