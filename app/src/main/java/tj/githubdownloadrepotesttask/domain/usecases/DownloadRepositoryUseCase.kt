package tj.githubdownloadrepotesttask.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import tj.githubdownloadrepotesttask.domain.repository.GithubRepository
import javax.inject.Inject

data class DownloadRepositoryParam(val owner: String, val repo: String)

interface DownloadRepositoryUseCase : FlowUseCase<DownloadRepositoryParam, ResponseBody>

class DownloadRepositoryUseCaseImpl @Inject constructor(
    private val repository: GithubRepository
) : DownloadRepositoryUseCase {

    override fun execute(param: DownloadRepositoryParam): Flow<ResponseBody> = flow {
        emit(repository.downloadRepository(owner = param.owner, repo = param.repo))
    }
}