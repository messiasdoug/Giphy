package ca.com.freshworks.network.factory

import ca.com.freshworks.network.BuildConfig
import ca.com.freshworks.network.GiphyApi
import ca.com.freshworks.network.interceptor.RequestInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitFactoryTest {

    private val moshi = Moshi.Builder().build()
    private val retrofitBuilder = Retrofit.Builder()
    private val httpClientBuilder = OkHttpClient.Builder()
    private val coroutineCallAdapterFactory = CoroutineCallAdapterFactory()
    private val requestInterceptor: RequestInterceptor = mockk()
    private val logInterceptor: HttpLoggingInterceptor = mockk()

    private val retrofitFactory = RetrofitFactory(
        moshi,
        retrofitBuilder,
        httpClientBuilder,
        coroutineCallAdapterFactory,
        requestInterceptor,
        logInterceptor
    )

    @Test
    fun checksIfInterceptorsHaveBeenAdded() {
        retrofitFactory.createService(GiphyApi::class.java)

        val addedInterceptors = httpClientBuilder.interceptors()

        assertThat(addedInterceptors).containsExactly(requestInterceptor, logInterceptor)
    }

    @Test
    fun checkIfTheBaseUrlHasBeenAdded() {
        retrofitFactory.createService(GiphyApi::class.java)

        val actualRetrofitBuilder = retrofitBuilder.build()

        assertThat(actualRetrofitBuilder.baseUrl().toUri().toASCIIString()).isEqualTo(BuildConfig.BASE_URL_GIPHY)
    }

    @Test
    fun checkIfAdapterFactoriesHasBeenAdded() {
        retrofitFactory.createService(GiphyApi::class.java)

        val actualRetrofitBuilder = retrofitBuilder.build()

        assertThat(actualRetrofitBuilder.callAdapterFactories()[0]).isInstanceOf(CoroutineCallAdapterFactory::class.java)
    }

    @Test
    fun checkIfConverterFactoriesHasBeenAdded() {
        retrofitFactory.createService(GiphyApi::class.java)

        val actualRetrofitBuilder = retrofitBuilder.build()

        assertThat(actualRetrofitBuilder.converterFactories()).hasAtLeastOneElementOfType(Converter.Factory::class.java)
    }

    @Test
    fun checkIfTargetHttpClientBuilderTimeoutsIsEqual30000() {
        retrofitFactory.createService(GiphyApi::class.java)

        val targetHttpClient = httpClientBuilder.build()

        assertThat(targetHttpClient.readTimeoutMillis).isEqualTo(30000)
        assertThat(targetHttpClient.connectTimeoutMillis).isEqualTo(30000)
        assertThat(targetHttpClient.writeTimeoutMillis).isEqualTo(30000)
    }
}