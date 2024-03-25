package com.okifirsyah.githubmate.use_case

import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.data.repository.GitHubUserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GitHubUserUseCaseImpl(private val repository: GitHubUserRepositoryImpl) :
    GitHubUserUseCase() {

    override suspend fun getAuthenticatedUser(): Flow<ApiResponse<GitHubUserResponse>> {
        return repository.fetchAuthenticatedUser().flowOn(Dispatchers.IO)
    }

    override suspend fun getUsers(): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return repository.fetchUsers().flowOn(Dispatchers.IO)
    }

    override suspend fun getUserDetail(username: String): Flow<ApiResponse<GitHubUserResponse>> {
        return repository.fetchUserDetail(username).flowOn(Dispatchers.IO)
    }

    override suspend fun getSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return repository.fetchSearchUserResult(username).flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollower(username: String): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return repository.fetchUserFollower(username).flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return repository.fetchUserFollowing(username).flowOn(Dispatchers.IO)
    }

    suspend fun getDashboardData() : Flow<ApiResponse<DashboardDto>> {
        return repository.getDashboardData().flowOn(Dispatchers.IO)
    }
}
