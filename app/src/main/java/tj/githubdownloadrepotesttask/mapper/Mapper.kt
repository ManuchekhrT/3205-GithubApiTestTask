package tj.githubdownloadrepotesttask.mapper

import tj.githubdownloadrepotesttask.data.local.entity.DownloadRepositoryEntity
import tj.githubdownloadrepotesttask.data.remote.dto.RepositoryDto
import tj.githubdownloadrepotesttask.presentation.model.Repository

fun RepositoryDto.asPresentationModel() = Repository(
    id = id,
    name = name ?: "",
    htmlUrl = htmlUrl ?: "",
    description = description ?: "",
    ownerLogin = owner?.login ?: "",
    ownerAvatarUrl = owner?.avatarUrl ?: ""
)

fun RepositoryDto.asEntityModel() = DownloadRepositoryEntity(
    name = name ?: "",
    login = owner?.login ?: "",
    url = htmlUrl ?: ""
)

fun DownloadRepositoryEntity.asPresentationModel() = Repository(
    id = id,
    name = name,
    htmlUrl = url,
    description = "",
    ownerLogin = login,
    ownerAvatarUrl = ""
)