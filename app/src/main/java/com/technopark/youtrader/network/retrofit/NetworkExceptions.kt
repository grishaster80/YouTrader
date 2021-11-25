package com.technopark.youtrader.network.retrofit

import com.technopark.youtrader.utils.Constants

class ApiErrorException(val error: String, val code: Int) : Exception() {
    override val message: String
        get() = Constants.API_ERROR_MESSAGE.format(error, code)
}

class NetworkFailureException : Exception() {
    override val message: String
        get() = Constants.NETWORK_FAILURE_MESSAGE
}
