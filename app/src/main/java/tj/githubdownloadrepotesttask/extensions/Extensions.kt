package tj.githubdownloadrepotesttask.extensions

import android.app.Activity
import android.os.Environment
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import tj.githubdownloadrepotesttask.presentation.model.DownloadFileState
import java.io.File

fun ResponseBody.saveFile(fileName: String): Flow<DownloadFileState> {
    return callbackFlow {

        val downloadsFolder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val destinationFile = File(downloadsFolder, "$fileName.zip")

        try {
            byteStream().use { inputStream->
                destinationFile.outputStream().use { outputStream->
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var progressBytes = 0L
                    var bytes = inputStream.read(buffer)
                    while (bytes >= 0) {
                        outputStream.write(buffer, 0, bytes)
                        progressBytes += bytes
                        bytes = inputStream.read(buffer)
                    }
                }
            }
            trySend(DownloadFileState.Finished)
        } catch (e: Exception) {
            trySend(DownloadFileState.Failed(e))
        }

        awaitClose {}
    }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
}

val Activity.rootView: View
    get() = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)

inline fun View.waitForLayout(crossinline f: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}
