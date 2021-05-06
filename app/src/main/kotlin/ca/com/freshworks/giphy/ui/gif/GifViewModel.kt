package ca.com.freshworks.giphy.ui.gif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.com.freshworks.domain.Gif
import ca.com.freshworks.giphy.ui.base.BaseViewModel
import ca.com.freshworks.interactor.favorite.FavoriteGifInteractor
import ca.com.freshworks.interactor.search.SearchGifInteractor
import ca.com.freshworks.interactor.trending.TrendingGifInteractor

class GifViewModel(
    private val trendingGifInteractor: TrendingGifInteractor,
    private val favoriteGifInteractor: FavoriteGifInteractor,
    private val searchGifInteractor: SearchGifInteractor
) : BaseViewModel() {

    private val trendingGifs = mutableListOf<Gif>()

    private val _gifsDisplayed = MutableLiveData<List<Gif>>()
    val gifsDisplayed: LiveData<List<Gif>> get() = _gifsDisplayed

    private val _favoriteGifsDisplayed = MutableLiveData<List<Gif>>()
    val favoriteGifsDisplayed: LiveData<List<Gif>> get() = _favoriteGifsDisplayed

    private val _addFavoriteSuccessful = MutableLiveData<Gif>()
    val favoriteSuccessful: LiveData<Gif> get() = _addFavoriteSuccessful

    private val _removeFavoriteSuccessful = MutableLiveData<Gif>()
    val removeSuccessful: LiveData<Gif> get() = _removeFavoriteSuccessful

    init {
        launchWithCoroutineScope {
            loadFavoriteGifs()
            trendingGifs.addAll(trendingGifInteractor.fetch())
            loadTrendingGifs()
        }
    }

    fun addToFavorites(gif: Gif) {
        launchWithCoroutineScope {
            favoriteGifInteractor.save(gif)
            loadFavoriteGifs()
            _addFavoriteSuccessful.value = gif.copy(isFavorite = true)
        }
    }

    fun removeFromFavorites(gif: Gif) {
        launchWithCoroutineScope {
            favoriteGifInteractor.remove(gif)
            loadFavoriteGifs()
            _removeFavoriteSuccessful.value = gif.copy(isFavorite = false)
        }
    }

    fun searchGifsBy(text: String) {
        launchWithCoroutineScope {
            _gifsDisplayed.value = gifsWithFavorites(searchGifInteractor.findBy(text))
        }
    }

    fun loadTrendingGifs() {
        launchWithCoroutineScope {
            _gifsDisplayed.value = gifsWithFavorites(trendingGifs)
        }
    }

    private suspend fun loadFavoriteGifs() {
        _favoriteGifsDisplayed.value = favoriteGifInteractor.fetch()
    }

    private fun gifsWithFavorites(gifs: List<Gif>): List<Gif> = gifs.map { gif ->
        if (_favoriteGifsDisplayed.value?.any { favoriteGif -> favoriteGif.id == gif.id } == true) {
            gif.copy(isFavorite = true)
        } else {
            gif
        }
    }
}