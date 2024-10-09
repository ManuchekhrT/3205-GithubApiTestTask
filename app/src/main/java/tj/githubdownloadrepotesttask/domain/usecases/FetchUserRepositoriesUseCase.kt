package tj.githubdownloadrepotesttask.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.githubdownloadrepotesttask.domain.repository.GithubRepository
import tj.githubdownloadrepotesttask.presentation.model.Repository
import javax.inject.Inject

interface FetchUserRepositoriesUseCase : FlowUseCase<String, List<Repository>>

class FetchUserRepositoriesUseCaseImpl @Inject constructor(
    private val repository: GithubRepository
) : FetchUserRepositoriesUseCase {

    override fun execute(param: String): Flow<List<Repository>> = flow {
        emit(repository.getUserRepositories(name = param))
    }

}