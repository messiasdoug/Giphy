package ca.com.freshworks.giphy.ui.gif

import androidx.recyclerview.widget.RecyclerView
import ca.com.freshworks.domain.Gif
import ca.com.freshworks.giphy.R
import ca.com.freshworks.giphy.databinding.ViewGifItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

class GifViewHolder(private val view: ViewGifItemBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(gif: Gif, callback: (Gif) -> Unit) {
        view.gif = gif

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(view.root)
            .asGif()
            .load(gif.url)
            .transition(withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.circle_progress)
            .into(view.gifMotion)

        view.favoriteButton.setOnClickListener {
            callback.invoke(gif)
        }
    }
}