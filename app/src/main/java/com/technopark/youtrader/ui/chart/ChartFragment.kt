package com.technopark.youtrader.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.ChartFragmentBinding
class ChartFragment : BaseFragment(R.layout.chart_fragment) {
    private val binding by viewBinding(ChartFragmentBinding::bind)

    override val viewModel: ChartViewModel by viewModels()

    private lateinit var lineChart: LineChart
    private var scoreList = ArrayList<Score>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lineChart = chart
        }

        initLineChart()

        setDataToLineChart()
    }

    private fun initLineChart() {

//        hide grid lines
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        // remove right y-axis
        lineChart.axisRight.isEnabled = false

        // remove legend
        lineChart.legend.isEnabled = false

        // remove description label
        lineChart.description.isEnabled = false

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
//        xAxis.labelRotationAngle = +90f
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < scoreList.size) {
                scoreList[index].date
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart() {
        val entries: ArrayList<Entry> = ArrayList()

        scoreList = getScoreList()

        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.score))
        }

        val lineDataSet = LineDataSet(entries, "")

        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER)
        lineDataSet.setColor(Color.BLACK)
        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }

    // simulate api call
    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("01.21", "34287.41".toFloat()))
        scoreList.add(Score("02.21", "46972.322499999995".toFloat()))
        scoreList.add(Score("03.21", "55298.89".toFloat()))
        scoreList.add(Score("04.21", "57270.72".toFloat()))
        scoreList.add(Score("05.21", "46863.76".toFloat()))
        scoreList.add(Score("06.21", "35756.145".toFloat()))
        scoreList.add(Score("07.21", "33659.42f".toFloat()))
        scoreList.add(Score("08.21", "46075.585".toFloat()))
        scoreList.add(Score("09.21", "45765.7625".toFloat()))
        scoreList.add(Score("10.21", "59887.82000000001".toFloat()))
        scoreList.add(Score("11.21", "62480.525".toFloat()))

        return scoreList
    }
}
