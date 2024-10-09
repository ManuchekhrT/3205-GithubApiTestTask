package tj.githubdownloadrepotesttask.data.local

import tj.githubdownloadrepotesttask.data.local.db.DownloadRepositoryDao
import tj.githubdownloadrepotesttask.data.local.entity.DownloadRepositoryEntity

class GithubLocalDataSource(
    private val dao: DownloadRepositoryDao
) {
    suspend fun addRepositoryToDownload(downloadedRepoEntity: DownloadRepositoryEntity) =
        dao.insertDownloadedRepo(downloadedRepoEntity)

    suspend fun getDownloadedRepositories() = dao.getAllDownloadedRepos()
}