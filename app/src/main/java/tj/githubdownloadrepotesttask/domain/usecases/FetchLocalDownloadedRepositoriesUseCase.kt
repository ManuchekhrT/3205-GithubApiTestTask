package tj.githubdownloadrepotesttask.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.githubdownloadrepotesttask.domain.repository.GithubRepository
import tj.githubdownloadrepotesttask.mapper.asPresentationModel
import tj.githubdownloadrepotesttask.presentation.model.Repository
import javax.inject.Inject

interface FetchLocalDownloadedRepositoriesUseCase : FlowUseCase<Unit, List<Repository>>

class FetchLocalDownloadedRepositoriesUseCaseImpl @Inject constructor(
    private val repository: GithubRepository
) : FetchLocalDownloadedRepositoriesUseCase {

    override fun execute(param: Unit): Flow<List<Repository>> = flow {
        emit(repository.getLocalDownloadedRepositories().map {
            it.asPresentationModel()
        })
    }

}