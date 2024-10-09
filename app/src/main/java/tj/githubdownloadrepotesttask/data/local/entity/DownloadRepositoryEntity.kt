package tj.githubdownloadrepotesttask.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
data class DownloadRepositoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "isDownloaded")
    val isDownloaded: Boolean = false
)