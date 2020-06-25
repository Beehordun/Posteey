package com.example.core.Interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header("x-api-key", "744e10a246a040b6a50d4acb2044478c")
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)

    }

}
