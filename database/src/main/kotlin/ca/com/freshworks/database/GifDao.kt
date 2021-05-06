package ca.com.freshworks.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.com.freshworks.database.entities.GifEntity

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gif: GifEntity)

    @Query("SELECT * FROM gifentity")
    fun queryAll(): List<GifEntity>

    @Delete
    fun delete(gif: GifEntity)
}