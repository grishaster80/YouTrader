package com.technopark.youtrader.utils

class Constants private constructor() {
    companion object {
        const val NR_AF_RT_IS_NOT_PARAMETRIZED_CALL = "return type must be parameterized +" +
            "as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>>"
        const val NR_AF_RT_IS_NOT_PARAMETRIZED_NETWORK_RESPONSE =
            "Response must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>"
        const val NETWORK_FAILURE_MESSAGE = "Network error occurred"
        const val API_ERROR_MESSAGE = "Api error: %s with code: %d"

        const val STANDARD_TIME_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        const val STANDARD_DATE_FORMAT = "yyyy-MM-dd"
        const val SIMPLE_DATE_FORMAT = "dd MMM yyyy"

        const val USD_SYMBOL = "$"

        const val PLUS_SYMBOL = '-'
        const val MINUS_SYMBOL = '-'

        const val STANDARD_PRECISION = "#.######"
        const val PERCENTAGE_PRECISION = "#.##"

        const val ARG_CURRENCY_ID = "currencyId"
    }
}
