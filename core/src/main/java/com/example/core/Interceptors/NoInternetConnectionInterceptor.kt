package com.example.core.Interceptors

import android.content.Context
import com.example.core.connectivity.NetworkInformation.isConnectionOn
import com.example.core.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoInternetConnectionInterceptor @Inject constructor(val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnectionOn(context)) {
            throw NoConnectivityException()
        } else {
            return chain.proceed(chain.request())
        }
    }
}