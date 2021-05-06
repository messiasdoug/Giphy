package ca.com.freshworks.interactor.search

import ca.com.freshworks.domain.Gif
import ca.com.freshworks.network.GiphyApi

class SearchGifInteractor(private val api: GiphyApi) {
    suspend fun findBy(text: String): List<Gif> = api.searchAsync(text).await().data
}