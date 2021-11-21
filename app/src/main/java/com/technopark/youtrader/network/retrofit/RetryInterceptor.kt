package com.technopark.youtrader.network.retrofit

import android.os.SystemClock
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        // try the request
        var response = chain.proceed(request)
        var tryCount = 0

        while (!response.isSuccessful && tryCount < 3) {
            response.close()
            tryCount++
            Log.d("intercept", "Request is not successful - $tryCount")

            SystemClock.sleep(3000)

            // retry the request
            response = chain.proceed(request)
        }

        // otherwise just pass the original response on
        Log.d("intercept", "Request is successful!")
        return response
    }
}
