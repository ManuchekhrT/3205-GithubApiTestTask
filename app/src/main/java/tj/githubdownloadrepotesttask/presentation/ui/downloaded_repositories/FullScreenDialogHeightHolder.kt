package tj.githubdownloadrepotesttask.presentation.ui.downloaded_repositories

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import tj.githubdownloadrepotesttask.extensions.rootView

class FullScreenDialogHeightHolder {

    var desiredHeight: Int = 0
        private set

    private val KEY_DESIRED_HEIGHT = "desired_height"

    fun saveDialogHeight(outState: Bundle) {
        outState.putInt(KEY_DESIRED_HEIGHT, desiredHeight)
    }

    fun calculateDialogHeight(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
        val rectangle = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rectangle)
        if (desiredHeight == 0) {
            desiredHeight = activity.rootView.height - rectangle.top
        }
        if (desiredHeight == 0) {
            desiredHeight = savedInstanceState?.getInt(KEY_DESIRED_HEIGHT) ?: 0
        }
    }
}