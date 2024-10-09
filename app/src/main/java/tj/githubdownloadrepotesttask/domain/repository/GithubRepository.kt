package tj.githubdownloadrepotesttask.domain.repository

import okhttp3.ResponseBody
import tj.githubdownloadrepotesttask.data.local.entity.DownloadRepositoryEntity
import tj.githubdownloadrepotesttask.presentation.model.Repository

interface GithubRepository {
    suspend fun getUserRepositories(name: String): List<Repository>
    suspend fun downloadRepository(owner: String, repo: String): ResponseBody
    suspend fun insertLocalDownloadedRepository(entity: DownloadRepositoryEntity)
    suspend fun getLocalDownloadedRepositories(): List<DownloadRepositoryEntity>
}