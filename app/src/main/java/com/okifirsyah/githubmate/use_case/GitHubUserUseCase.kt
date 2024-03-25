package com.okifirsyah.githubmate.use_case

import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import kotlinx.coroutines.flow.Flow

abstract class GitHubUserUseCase {

    abstract suspend fun getAuthenticatedUser(): Flow<ApiResponse<GitHubUserResponse>>

    abstract suspend fun getUsers(): Flow<ApiResponse<List<GitHubUserResponse>>>

    abstract suspend fun getUserDetail(username: String): Flow<ApiResponse<GitHubUserResponse>>

    abstract suspend fun getSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUserResponse>>>

    abstract suspend fun getUserFollower(username: String): Flow<ApiResponse<List<GitHubUserResponse>>>

    abstract suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<GitHubUserResponse>>>

}
