package tj.githubdownloadrepotesttask.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tj.githubdownloadrepotesttask.data.local.entity.DownloadRepositoryEntity

@Database(entities = [DownloadRepositoryEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadRepoDao(): DownloadRepositoryDao
}
