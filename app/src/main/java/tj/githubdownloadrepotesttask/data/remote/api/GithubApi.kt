package tj.githubdownloadrepotesttask.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import tj.githubdownloadrepotesttask.data.remote.dto.RepositoryDto

interface GithubApi {
    @GET("users/{name}/repos")
    suspend fun getUserRepositories(@Path("name") name: String): List<RepositoryDto>

    @GET("repos/{owner}/{repo}/zipball")
    suspend fun downloadRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): ResponseBody
}