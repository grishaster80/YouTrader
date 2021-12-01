package com.technopark.youtrader.utils

import com.technopark.youtrader.utils.Constants.Companion.STANDARD_PRECISION
import java.math.RoundingMode
import java.text.DecimalFormat

fun roundTo(number: Double, format: String = STANDARD_PRECISION) : String{
    val df = DecimalFormat(format)
    df.roundingMode = RoundingMode.CEILING
    return df.format(number)
}