package tj.githubdownloadrepotesttask.data.repository

import okhttp3.ResponseBody
import tj.githubdownloadrepotesttask.data.local.GithubLocalDataSource
import tj.githubdownloadrepotesttask.data.local.entity.DownloadRepositoryEntity
import tj.githubdownloadrepotesttask.data.remote.GithubRemoteDataSource
import tj.githubdownloadrepotesttask.domain.repository.GithubRepository
import tj.githubdownloadrepotesttask.presentation.model.Repository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val remoteDataSource: GithubRemoteDataSource,
    private val localDataSource: GithubLocalDataSource
) : GithubRepository {

    override suspend fun getUserRepositories(name: String): List<Repository> {
        return remoteDataSource.fetchUserRepositories(name)
    }

    override suspend fun downloadRepository(owner: String, repo: String): ResponseBody {
        return remoteDataSource.downloadRepository(owner, repo)
    }

    override suspend fun insertLocalDownloadedRepository(entity: DownloadRepositoryEntity) {
        localDataSource.addRepositoryToDownload(entity)
    }

    override suspend fun getLocalDownloadedRepositories(): List<DownloadRepositoryEntity> {
       return localDataSource.getDownloadedRepositories()
    }

}