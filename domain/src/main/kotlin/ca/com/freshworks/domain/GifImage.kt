package ca.com.freshworks.domain

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GifImage(
    val original: GifImageDetails
) : Parcelable
