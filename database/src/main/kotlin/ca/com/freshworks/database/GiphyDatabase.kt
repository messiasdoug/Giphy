package ca.com.freshworks.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.com.freshworks.database.entities.GifEntity

@Database(
    entities = [GifEntity::class],
    version = GiphyDatabase.VERSION
)
abstract class GiphyDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao

    companion object {
        const val VERSION = 1
    }
}