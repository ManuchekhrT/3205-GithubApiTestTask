package tj.githubdownloadrepotesttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.githubdownloadrepotesttask.domain.repository.GithubRepository
import tj.githubdownloadrepotesttask.domain.usecases.DownloadRepositoryUseCase
import tj.githubdownloadrepotesttask.domain.usecases.DownloadRepositoryUseCaseImpl
import tj.githubdownloadrepotesttask.domain.usecases.FetchLocalDownloadedRepositoriesUseCase
import tj.githubdownloadrepotesttask.domain.usecases.FetchLocalDownloadedRepositoriesUseCaseImpl
import tj.githubdownloadrepotesttask.domain.usecases.FetchUserRepositoriesUseCase
import tj.githubdownloadrepotesttask.domain.usecases.FetchUserRepositoriesUseCaseImpl
import tj.githubdownloadrepotesttask.domain.usecases.InsertLocalDownloadedRepositoryUseCase
import tj.githubdownloadrepotesttask.domain.usecases.InsertLocalDownloadedRepositoryUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideFetchUserReposUseCase(
        repository: GithubRepository
    ): FetchUserRepositoriesUseCase = FetchUserRepositoriesUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideDownloadRepoUseCase(
        repository: GithubRepository
    ): DownloadRepositoryUseCase = DownloadRepositoryUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideInsertLocalDownloadedRepositoryUseCase(
        repository: GithubRepository
    ): InsertLocalDownloadedRepositoryUseCase = InsertLocalDownloadedRepositoryUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideFetchLocalDownloadedRepositoriesUseCase(
        repository: GithubRepository
    ): FetchLocalDownloadedRepositoriesUseCase = FetchLocalDownloadedRepositoriesUseCaseImpl(repository)
}