package tj.githubdownloadrepotesttask.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import tj.githubdownloadrepotesttask.di.IoDispatcher
import tj.githubdownloadrepotesttask.domain.usecases.DownloadRepositoryParam
import tj.githubdownloadrepotesttask.domain.usecases.DownloadRepositoryUseCase
import tj.githubdownloadrepotesttask.domain.usecases.FetchUserRepositoriesUseCase
import tj.githubdownloadrepotesttask.domain.usecases.InsertLocalDownloadedRepositoryUseCase
import tj.githubdownloadrepotesttask.extensions.saveFile
import tj.githubdownloadrepotesttask.presentation.model.DownloadFileState
import tj.githubdownloadrepotesttask.presentation.model.Repository
import tj.githubdownloadrepotesttask.presentation.model.State
import javax.inject.Inject

typealias DownloadComplete = Pair<Repository, Boolean>

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchUserRepositoriesUseCase: FetchUserRepositoriesUseCase,
    private val downloadRepositoryUseCase: DownloadRepositoryUseCase,
    private val insertLocalDownloadedRepositoryUseCase: InsertLocalDownloadedRepositoryUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userReposState = MutableSharedFlow<State<List<Repository>>>()
    val userReposState: SharedFlow<State<List<Repository>>> get() = _userReposState

    private val _downloadComplete = MutableSharedFlow<DownloadComplete>()
    val downloadComplete: SharedFlow<DownloadComplete> get() = _downloadComplete

    init {
        viewModelScope.launch(ioDispatcher) {
            _userReposState.emit(State.Empty)
        }
    }

    fun search(q: String) {
        viewModelScope.launch(ioDispatcher) {
            _userReposState.emit(State.Loading)
            fetchUserRepositoriesUseCase.execute(q)
                .catch {
                    _userReposState.emit(State.Error(it))
                }
                .collectLatest {
                    _userReposState.emit(State.Success(it))
                }
        }
    }

    fun downloadRepo(repo: Repository) {
        viewModelScope.launch(ioDispatcher) {
            downloadRepositoryUseCase.execute(
                DownloadRepositoryParam(
                    owner = repo.ownerLogin,
                    repo = repo.name
                )
            ).flatMapLatest {
                it.saveFile(repo.name)
            }
                .collectLatest {
                    when(it) {
                        is DownloadFileState.Finished -> {
                            addToDownloads(repo)
                        }
                        is DownloadFileState.Failed -> {
                            _downloadComplete.emit(Pair(repo, false))
                        }
                    }
                }
        }
    }

    private fun addToDownloads(repo: Repository) {
        viewModelScope.launch(ioDispatcher) {
            insertLocalDownloadedRepositoryUseCase.execute(repo)
                .collectLatest {
                    _downloadComplete.emit(Pair(repo, true))
                }
        }
    }

}