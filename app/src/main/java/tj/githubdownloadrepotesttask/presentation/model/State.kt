package tj.githubdownloadrepotesttask.presentation.model

sealed class State<out R> {
    data class Success<out T>(
        val data: T
    ) : State<T>()

    object Empty : State<Nothing>()

    data class Error(
        val error: Throwable? = null,
        val message: String? = null
    ) : State<Nothing>()

    object Loading : State<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message]"
            is Loading  -> "Loading"
            else -> "Empty"
        }
    }
}

sealed class DownloadFileState {
    object Finished : DownloadFileState()
    data class Failed(val error: Throwable? = null) : DownloadFileState()
}