package com.okifirsyah.githubmate.data.network.service

import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubUserService {

    @GET("user")
    suspend fun fetchAuthenticatedUser(): GitHubUserResponse

    @GET("users")
    suspend fun fetchUsers(): List<GitHubUserResponse>

    @GET("users/{username}")
    suspend fun fetchUserDetail(
        @Path("username") username: String
    ): GitHubUserResponse

    @GET("search/users")
    suspend fun fetchSearchUserResult(
        @Query("q") username: String
    ): GitHubUserSearchResponse

    @GET("users/{username}/followers")
    suspend fun fetchUserFollower(
        @Path("username") username: String
    ): List<GitHubUserResponse>

    @GET("users/{username}/following")
    suspend fun fetchUserFollowing(
        @Path("username") username: String
    ): List<GitHubUserResponse>

}
