package tj.githubdownloadrepotesttask.data.remote

import okhttp3.ResponseBody
import tj.githubdownloadrepotesttask.data.remote.api.GithubApi
import tj.githubdownloadrepotesttask.mapper.asPresentationModel
import tj.githubdownloadrepotesttask.presentation.model.Repository

class GithubRemoteDataSource(
    private val api: GithubApi
) {
    suspend fun fetchUserRepositories(name: String): List<Repository> {
        return api.getUserRepositories(name).map {
            it.asPresentationModel()
        }
    }

    suspend fun downloadRepository(owner: String, repo: String): ResponseBody {
        return api.downloadRepository(owner, repo)
    }
}