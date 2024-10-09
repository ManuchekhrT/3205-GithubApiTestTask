package tj.githubdownloadrepotesttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.githubdownloadrepotesttask.data.local.GithubLocalDataSource
import tj.githubdownloadrepotesttask.data.local.db.DownloadRepositoryDao
import tj.githubdownloadrepotesttask.data.remote.GithubRemoteDataSource
import tj.githubdownloadrepotesttask.data.remote.api.GithubApi
import tj.githubdownloadrepotesttask.data.repository.GithubRepositoryImpl
import tj.githubdownloadrepotesttask.domain.repository.GithubRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideGithubRemoteDataSource(
        api: GithubApi
    ) = GithubRemoteDataSource(api)

    @Singleton
    @Provides
    fun provideGithubLocalDataSource(
        dao: DownloadRepositoryDao
    ) = GithubLocalDataSource(dao)

    @Singleton
    @Provides
    fun provideGithubRepository(
        dataSource: GithubRemoteDataSource,
        localDataSource: GithubLocalDataSource
    ): GithubRepository = GithubRepositoryImpl(dataSource, localDataSource)

}