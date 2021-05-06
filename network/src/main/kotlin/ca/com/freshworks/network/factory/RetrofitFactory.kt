package ca.com.freshworks.network.factory

import ca.com.freshworks.network.BuildConfig
import ca.com.freshworks.network.interceptor.RequestInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory(
    private val moshi: Moshi,
    private val retrofitBuilder: Retrofit.Builder,
    private val httpClientBuilder: OkHttpClient.Builder,
    private val coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
    private val requestInterceptor: RequestInterceptor,
    private val httpLoggingInterceptor: HttpLoggingInterceptor
) {
    fun <S> createService(serviceClass: Class<S>): S {
        val retrofitBuilder = retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL_GIPHY)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))

        configureTimeouts(httpClientBuilder)
        httpClientBuilder.addInterceptor(requestInterceptor)
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)

        retrofitBuilder.client(httpClientBuilder.build())

        return retrofitBuilder.build().create(serviceClass)
    }

    private fun configureTimeouts(httpClientBuilder: OkHttpClient.Builder) {
        httpClientBuilder
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
    }

    private companion object {
        private const val TIMEOUT = 30L
    }
}