package com.okifirsyah.githubmate.data.repository

import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow

abstract class GitHubUserRepository {
    abstract fun fetchDashboardData(): Flow<ApiResponse<DashboardDto>>
    abstract fun fetchauthorizedUser(): Flow<ApiResponse<GitHubUser>>
    abstract fun fetchUsers(): Flow<ApiResponse<List<GitHubUser>>>
    abstract fun fetchUserDetail(username: String): Flow<ApiResponse<GitHubUser>>
    abstract fun fetchSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUser>>>
    abstract fun fetchUserFollower(username: String): Flow<ApiResponse<List<GitHubUser>>>
    abstract fun fetchUserFollowing(username: String): Flow<ApiResponse<List<GitHubUser>>>
    abstract fun fetchFavoriteUsers(): Flow<ApiResponse<List<GitHubUser>>>
    abstract suspend fun updateUserFavorite(username: String, isFavorite: Boolean): Unit

}
