package com.okifirsyah.githubmate.data.network.service

import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.response.GitHubUserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubUserService {

    @GET("user")
    suspend fun fetchauthorizedUser(): GitHubUser

    @GET("users")
    suspend fun fetchUsers(): List<GitHubUser>

    @GET("users/{username}")
    suspend fun fetchUserDetail(
        @Path("username") username: String
    ): GitHubUser

    @GET("search/users")
    suspend fun fetchSearchUserResult(
        @Query("q") username: String
    ): GitHubUserSearchResponse

    @GET("users/{username}/followers")
    suspend fun fetchUserFollower(
        @Path("username") username: String
    ): List<GitHubUser>

    @GET("users/{username}/following")
    suspend fun fetchUserFollowing(
        @Path("username") username: String
    ): List<GitHubUser>

}
