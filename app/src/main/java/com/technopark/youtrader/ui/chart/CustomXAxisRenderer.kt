package com.technopark.youtrader.ui.chart

import android.graphics.Canvas
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.utils.ViewPortHandler
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils

class CustomXAxisRenderer(viewPortHandler: ViewPortHandler?, xAxis: XAxis?, trans: Transformer?) :
    XAxisRenderer(viewPortHandler, xAxis, trans) {
    override fun drawLabel(
        c: Canvas?,
        formattedLabel: String,
        x: Float,
        y: Float,
        anchor: MPPointF?,
        angleDegrees: Float
    ) {
        val line = formattedLabel.split("\n").toTypedArray()
        Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angleDegrees)
        Utils.drawXAxisValue(
            c,
            line[1],
            x + mAxisLabelPaint.textSize,
            y + mAxisLabelPaint.textSize,
            mAxisLabelPaint,
            anchor,
            angleDegrees
        )
    }
}