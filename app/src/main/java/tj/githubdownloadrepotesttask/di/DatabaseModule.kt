package tj.githubdownloadrepotesttask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tj.githubdownloadrepotesttask.data.local.db.AppDatabase
import tj.githubdownloadrepotesttask.data.local.db.DownloadRepositoryDao
import javax.inject.Singleton

const val DB_NAME = "downloaded_repos_database"
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideItemDao(database: AppDatabase): DownloadRepositoryDao {
        return database.downloadRepoDao()
    }
}