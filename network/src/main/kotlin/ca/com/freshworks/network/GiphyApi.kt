package ca.com.freshworks.network

import ca.com.freshworks.domain.GifResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("v1/gifs/trending")
    fun trendingAsync(): Deferred<GifResponse>

    @GET("v1/gifs/search")
    fun searchAsync(@Query("q") text: String): Deferred<GifResponse>
}