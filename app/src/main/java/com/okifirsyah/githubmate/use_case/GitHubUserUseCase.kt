package com.okifirsyah.githubmate.use_case

import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow

abstract class GitHubUserUseCase {

    abstract suspend fun getAuthorizedUser(): Flow<ApiResponse<GitHubUser>>
    abstract suspend fun getUsers(): Flow<ApiResponse<List<GitHubUser>>>
    abstract suspend fun getUserDetail(username: String): Flow<ApiResponse<GitHubUser>>
    abstract suspend fun getSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUser>>>
    abstract suspend fun getUserFollower(username: String): Flow<ApiResponse<List<GitHubUser>>>
    abstract suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<GitHubUser>>>
    abstract suspend fun getDashboardData(): Flow<ApiResponse<DashboardDto>>

    abstract suspend fun getFavoriteUser(): Flow<ApiResponse<List<GitHubUser>>>
    abstract suspend fun setFavoriteUser(username: String, isFavorite: Boolean): Unit

}
