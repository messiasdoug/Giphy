package ca.com.freshworks.giphy.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ca.com.freshworks.giphy.R
import ca.com.freshworks.giphy.ui.favorite.FavoriteFragment
import ca.com.freshworks.giphy.ui.trending.TrendingFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_item_trending,
    R.string.tab_item_favorites
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> TrendingFragment.newInstance()
        else -> FavoriteFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = 2
}