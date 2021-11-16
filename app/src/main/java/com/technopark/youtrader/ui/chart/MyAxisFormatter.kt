package com.technopark.youtrader.ui.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class MyAxisFormatter(private var scoreList: ArrayList<Score>) : IndexAxisValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val index = value.toInt()
        return if (index < scoreList.size) {
            scoreList[index].date
        } else {
            ""
        }
    }
}
