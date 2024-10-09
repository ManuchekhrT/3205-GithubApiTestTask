package tj.githubdownloadrepotesttask.presentation.ui.downloaded_repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.githubdownloadrepotesttask.di.IoDispatcher
import tj.githubdownloadrepotesttask.domain.usecases.FetchLocalDownloadedRepositoriesUseCase
import tj.githubdownloadrepotesttask.presentation.model.Repository
import tj.githubdownloadrepotesttask.presentation.model.State
import javax.inject.Inject

@HiltViewModel
class DownloadedRepositoriesViewModel @Inject constructor(
    private val useCase: FetchLocalDownloadedRepositoriesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _downloadedReposState = MutableStateFlow<State<List<Repository>>>(State.Loading)
    val downloadedReposState: StateFlow<State<List<Repository>>> get() = _downloadedReposState

    init {
        _downloadedReposState.value = State.Loading
        viewModelScope.launch(ioDispatcher) {
            useCase.execute(Unit)
                .catch {
                    _downloadedReposState.value = State.Error(it)
                }
                .collectLatest {
                    if (it.isEmpty()) {
                        _downloadedReposState.value = State.Empty
                    } else {
                        _downloadedReposState.value = State.Success(it)
                    }
                }
        }
    }

}