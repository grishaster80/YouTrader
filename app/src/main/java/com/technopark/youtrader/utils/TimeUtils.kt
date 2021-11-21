package com.technopark.youtrader.utils

import android.annotation.SuppressLint
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun timestampToFormatDate(
    timestamp: Long,
    pattern: String = Constants.STANDARD_TIME_DATE_FORMAT
): String {
    return SimpleDateFormat(pattern).format(
        Date(
            Timestamp(timestamp).time
        )
    )
}
