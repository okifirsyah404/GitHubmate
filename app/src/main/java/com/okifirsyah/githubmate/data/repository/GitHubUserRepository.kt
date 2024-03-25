package com.okifirsyah.githubmate.data.repository

import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import kotlinx.coroutines.flow.Flow

abstract class GitHubUserRepository {

    abstract fun fetchAuthenticatedUser(): Flow<ApiResponse<GitHubUserResponse>>

    abstract fun fetchUsers(): Flow<ApiResponse<List<GitHubUserResponse>>>

    abstract fun fetchUserDetail(username: String): Flow<ApiResponse<GitHubUserResponse>>

    abstract fun fetchSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUserResponse>>>

    abstract fun fetchUserFollower(username: String): Flow<ApiResponse<List<GitHubUserResponse>>>

    abstract fun fetchUserFollowing(username: String): Flow<ApiResponse<List<GitHubUserResponse>>>

}
