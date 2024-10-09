package tj.githubdownloadrepotesttask.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.githubdownloadrepotesttask.data.local.entity.DownloadRepositoryEntity
import tj.githubdownloadrepotesttask.domain.repository.GithubRepository
import tj.githubdownloadrepotesttask.presentation.model.Repository
import javax.inject.Inject

interface InsertLocalDownloadedRepositoryUseCase : FlowUseCase<Repository, Unit>

class InsertLocalDownloadedRepositoryUseCaseImpl @Inject constructor(
    private val repository: GithubRepository
) : InsertLocalDownloadedRepositoryUseCase {

    override fun execute(param: Repository): Flow<Unit> = flow {
        val entity = DownloadRepositoryEntity(
            name = param.name,
            login = param.ownerLogin,
            url = param.htmlUrl,
            isDownloaded = true
        )
        emit(repository.insertLocalDownloadedRepository(entity))
    }
}