package com.okifirsyah.githubmate.use_case

import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.repository.GitHubUserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GitHubUserUseCaseImpl(private val repository: GitHubUserRepositoryImpl) :
    GitHubUserUseCase() {

    override suspend fun getAuthorizedUser(): Flow<ApiResponse<GitHubUser>> {
        return repository.fetchauthorizedUser().flowOn(Dispatchers.IO)
    }

    override suspend fun getUsers(): Flow<ApiResponse<List<GitHubUser>>> {
        return repository.fetchUsers().flowOn(Dispatchers.IO)
    }

    override suspend fun getUserDetail(username: String): Flow<ApiResponse<GitHubUser>> {
        return repository.fetchUserDetail(username).flowOn(Dispatchers.IO)
    }

    override suspend fun getSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUser>>> {
        return repository.fetchSearchUserResult(username).flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollower(username: String): Flow<ApiResponse<List<GitHubUser>>> {
        return repository.fetchUserFollower(username).flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<GitHubUser>>> {
        return repository.fetchUserFollowing(username).flowOn(Dispatchers.IO)
    }

    override suspend fun getDashboardData(): Flow<ApiResponse<DashboardDto>> {
        return repository.fetchDashboardData().flowOn(Dispatchers.IO)
    }

    override suspend fun getFavoriteUser(): Flow<ApiResponse<List<GitHubUser>>> {
        return repository.fetchFavoriteUsers().flowOn(Dispatchers.IO)
    }

    override suspend fun setFavoriteUser(username: String, isFavorite: Boolean): Unit {
        repository.updateUserFavorite(username, isFavorite)
    }
}
