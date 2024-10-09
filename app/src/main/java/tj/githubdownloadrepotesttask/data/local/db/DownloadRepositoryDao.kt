package tj.githubdownloadrepotesttask.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tj.githubdownloadrepotesttask.data.local.entity.DownloadRepositoryEntity

@Dao
interface DownloadRepositoryDao {
    @Query("SELECT * FROM downloads")
    suspend fun getAllDownloadedRepos(): List<DownloadRepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloadedRepo(item: DownloadRepositoryEntity)
}