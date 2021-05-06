package ca.com.freshworks.interactor.trending

import ca.com.freshworks.domain.Gif
import ca.com.freshworks.network.GiphyApi

class TrendingGifInteractor(
    private val api: GiphyApi
) {
    suspend fun fetch(): List<Gif> = api.trendingAsync().await().data
}