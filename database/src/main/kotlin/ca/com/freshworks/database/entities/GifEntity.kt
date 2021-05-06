package ca.com.freshworks.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.com.freshworks.domain.Gif
import ca.com.freshworks.domain.GifImage
import ca.com.freshworks.domain.GifImageDetails

@Entity
data class GifEntity(
    @PrimaryKey val id: String,
    val type: String,
    val url: String
)

fun GifEntity.toDomain(): Gif = Gif(id, type, GifImage(GifImageDetails(url)), true)

fun Gif.toEntity(): GifEntity = GifEntity(id, type, this.images.original.url)