package com.technopark.youtrader.network.auth

import com.technopark.youtrader.utils.Constants

class FirebaseUnknownErrorException : Exception() {
    override val message: String
        get() = Constants.FIREBASE_UNKNOWN_ERROR_MESSAGE
}
