package com.technopark.youtrader.network.retrofit

import com.technopark.youtrader.network.NetworkResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<S : Any>(
    private val successType: Type,
) : CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type {
        return successType
    }

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call)
    }
}
