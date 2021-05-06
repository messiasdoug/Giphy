package ca.com.freshworks.giphy.ui.design

import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import ca.com.freshworks.giphy.R
import com.google.android.material.snackbar.Snackbar

class GiphySnackBar {
    companion object {
        fun show(view: View, @StringRes resId: Int, duration: Int) {
            val snackBar = Snackbar.make(view, resId, duration)
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.orange))
            snackBar.show()
        }
    }
}