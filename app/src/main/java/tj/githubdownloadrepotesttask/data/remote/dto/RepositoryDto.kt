package tj.githubdownloadrepotesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RepositoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("owner")
    val owner: RepositoryOwner? = null,
    @SerializedName("html_url")
    val htmlUrl: String? = "",
    @SerializedName("description")
    val description: String? = ""
)

data class RepositoryOwner(
    @SerializedName("login")
    val login: String? = "",
    @SerializedName("avatarUrl")
    val avatarUrl: String? = ""
)