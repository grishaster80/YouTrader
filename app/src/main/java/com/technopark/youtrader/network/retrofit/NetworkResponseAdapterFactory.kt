package com.technopark.youtrader.network.retrofit

import com.technopark.youtrader.utils.Constants
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }
        check(returnType is ParameterizedType) {
            Constants.NR_AF_RT_IS_NOT_PARAMETRIZED_CALL
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            Constants.NR_AF_RT_IS_NOT_PARAMETRIZED_NETWORK_RESPONSE
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        return NetworkResponseAdapter<Any>(successBodyType)
    }
}
