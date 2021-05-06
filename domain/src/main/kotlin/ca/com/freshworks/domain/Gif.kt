package ca.com.freshworks.domain

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Gif(
    val id: String,
    val type: String,
    val images: GifImage,
    val isFavorite: Boolean = false
) : Parcelable {
    val url: String get() = images.original.url

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Gif

        return (other.id == id)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + images.hashCode()
        result = 31 * result + isFavorite.hashCode()
        return result
    }


}