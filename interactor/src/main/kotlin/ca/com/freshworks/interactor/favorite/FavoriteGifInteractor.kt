package ca.com.freshworks.interactor.favorite

import ca.com.freshworks.database.GifDao
import ca.com.freshworks.database.entities.toDomain
import ca.com.freshworks.database.entities.toEntity
import ca.com.freshworks.domain.Gif
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FavoriteGifInteractor(
    private val gifDao: GifDao,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun fetch(): List<Gif> = withContext(coroutineDispatcher) {
        gifDao.queryAll().map { it.toDomain() }
    }

    suspend fun save(gif: Gif) = withContext(coroutineDispatcher) {
        gifDao.insert(gif.toEntity())
    }

    suspend fun remove(gif: Gif) = withContext(coroutineDispatcher) {
        gifDao.delete(gif.toEntity())
    }
}