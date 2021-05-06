package ca.com.freshworks.network.module

import ca.com.freshworks.network.GiphyApi
import ca.com.freshworks.network.factory.RetrofitFactory
import ca.com.freshworks.network.interceptor.RequestInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    factory { Moshi.Builder().build() }
    factory { RequestInterceptor() }
    factory {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    factory {
        RetrofitFactory(
            get(),
            Retrofit.Builder(),
            OkHttpClient.Builder(),
            CoroutineCallAdapterFactory(),
            get(),
            get()
        )
    }
    factory {
        (get(clazz = RetrofitFactory::class) as RetrofitFactory)
            .createService(GiphyApi::class.java)
    }
}