package ca.com.freshworks.giphy.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import ca.com.freshworks.domain.Gif
import ca.com.freshworks.giphy.R
import ca.com.freshworks.giphy.databinding.FragmentTrendingBinding
import ca.com.freshworks.giphy.ui.design.GiphySnackBar
import ca.com.freshworks.giphy.ui.gif.GifAdapter
import ca.com.freshworks.giphy.ui.gif.GifViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TrendingFragment private constructor() : Fragment() {

    private lateinit var binding: FragmentTrendingBinding

    private val viewModel by sharedViewModel<GifViewModel>()
    private val gifAdapter = GifAdapter(::callback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.trendingItems.adapter = gifAdapter

        observer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.searchGifsBy(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return when {
                        newText.length > 3 -> {
                            viewModel.searchGifsBy(newText)
                            true
                        }
                        newText.isEmpty() -> {
                            viewModel.loadTrendingGifs()
                            true
                        }
                        else -> false
                    }
                }
            })

            setOnCloseListener {
                viewModel.loadTrendingGifs()
                false
            }

            setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus.not()) {
                    viewModel.loadTrendingGifs()
                }
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun observer() {
        viewModel.gifsDisplayed.observe(viewLifecycleOwner, { gifs ->
            gifAdapter.add(gifs)
        })

        viewModel.favoriteSuccessful.observe(viewLifecycleOwner, { gif ->
            gifAdapter.update(gif)
            GiphySnackBar.show(
                requireView(),
                R.string.message_gif_add_favorites_successful,
                Snackbar.LENGTH_LONG
            )
        })

        viewModel.removeSuccessful.observe(viewLifecycleOwner, { gif ->
            gifAdapter.update(gif)
            GiphySnackBar.show(
                requireView(),
                R.string.message_gif_remove_favorites_successful,
                Snackbar.LENGTH_LONG
            )
        })
    }

    private fun callback(gif: Gif) {
        if (gif.isFavorite) {
            viewModel.removeFromFavorites(gif)
        } else {
            viewModel.addToFavorites(gif)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment = TrendingFragment()
    }
}