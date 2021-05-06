package ca.com.freshworks.network.interceptor

import ca.com.freshworks.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, BuildConfig.API_KEY_GIPHY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(newUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private companion object {
        const val QUERY_PARAM_API_KEY = "api_key"
    }
}