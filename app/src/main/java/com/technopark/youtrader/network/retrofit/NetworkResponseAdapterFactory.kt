package com.technopark.youtrader.network.retrofit

import android.content.res.Resources
import com.technopark.youtrader.R
import com.technopark.youtrader.network.NetworkResponse
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
            Resources.getSystem().getString(R.string.network_response_adapter_factory_return_type_is_not_parametrized_call)
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            Resources.getSystem().getString(R.string.network_response_adapter_factory_return_type_is_not_parametrized_network_response)
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        return NetworkResponseAdapter<Any>(successBodyType)
    }
}
