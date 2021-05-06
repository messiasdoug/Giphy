package ca.com.freshworks.interactor.module

import ca.com.freshworks.interactor.favorite.FavoriteGifInteractor
import ca.com.freshworks.interactor.search.SearchGifInteractor
import ca.com.freshworks.interactor.trending.TrendingGifInteractor
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val interactorModule = module {
    factory { FavoriteGifInteractor(get(), Dispatchers.IO) }
    factory { TrendingGifInteractor(get()) }
    factory { SearchGifInteractor(get()) }
}