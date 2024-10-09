package tj.githubdownloadrepotesttask.presentation.model

data class Repository(
    val id: Int,
    val name: String,
    val htmlUrl: String,
    val description: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String
)