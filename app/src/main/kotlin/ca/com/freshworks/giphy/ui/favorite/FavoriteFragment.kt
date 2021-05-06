package ca.com.freshworks.giphy.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.com.freshworks.domain.Gif
import ca.com.freshworks.giphy.R
import ca.com.freshworks.giphy.databinding.FragmentFavoriteBinding
import ca.com.freshworks.giphy.ui.design.GiphySnackBar
import ca.com.freshworks.giphy.ui.gif.GifAdapter
import ca.com.freshworks.giphy.ui.gif.GifViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteFragment private constructor() : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel by sharedViewModel<GifViewModel>()
    private val gifAdapter = GifAdapter(::callback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteItems.adapter = gifAdapter

        observer()
    }

    private fun observer() {
        viewModel.favoriteGifsDisplayed.observe(viewLifecycleOwner, { gifs ->
            gifAdapter.add(gifs)
        })

        viewModel.favoriteSuccessful.observe(viewLifecycleOwner, { gif ->
            gifAdapter.add(gif)
            GiphySnackBar.show(
                requireView(),
                R.string.message_gif_add_favorites_successful,
                Snackbar.LENGTH_LONG
            )
        })

        viewModel.removeSuccessful.observe(viewLifecycleOwner, { gif ->
            gifAdapter.remove(gif)
            GiphySnackBar.show(
                requireView(),
                R.string.message_gif_remove_favorites_successful,
                Snackbar.LENGTH_LONG
            )
        })
    }

    private fun callback(gif: Gif) {
        viewModel.removeFromFavorites(gif)
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment = FavoriteFragment()
    }
}