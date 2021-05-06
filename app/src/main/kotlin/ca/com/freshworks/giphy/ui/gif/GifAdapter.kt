package ca.com.freshworks.giphy.ui.gif

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.com.freshworks.domain.Gif
import ca.com.freshworks.giphy.databinding.ViewGifItemBinding

class GifAdapter(
    private val callback: (Gif) -> Unit
) : RecyclerView.Adapter<GifViewHolder>() {

    private val gifs = mutableListOf<Gif>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = ViewGifItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(gifs[position], callback)
    }

    override fun getItemCount(): Int = gifs.size

    fun add(gifs: List<Gif>) {
        this.gifs.clear()
        this.gifs.addAll(gifs)
        notifyDataSetChanged()
    }

    fun add(gif: Gif) {
        this.gifs.add(gif)
        val position = if (this.gifs.size > 0) gifs.size + 1 else 0
        notifyItemInserted(position)
    }

    fun update(gif: Gif) {
        val position = this.gifs.indexOf(gif)
        if (position != -1) {
            this.gifs[position] = gif
            notifyItemChanged(position)
        }
    }

    fun remove(gif: Gif) {
        val position = this.gifs.indexOf(gif)
        if (position != -1) {
            this.gifs.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}