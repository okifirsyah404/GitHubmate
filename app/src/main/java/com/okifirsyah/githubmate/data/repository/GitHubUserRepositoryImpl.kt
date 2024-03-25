package com.okifirsyah.githubmate.data.repository

import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.data.network.service.GitHubUserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubUserRepositoryImpl(private val service: GitHubUserService) : GitHubUserRepository() {

    fun getDashboardData(): Flow<ApiResponse<DashboardDto>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val authenticatedUserResponse = service.fetchAuthenticatedUser()
                val usersResponse = service.fetchUsers()
                emit(ApiResponse.Success(DashboardDto(authenticatedUserResponse, usersResponse)))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchAuthenticatedUser(): Flow<ApiResponse<GitHubUserResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchAuthenticatedUser()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchUsers(): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUsers()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchUserDetail(username: String): Flow<ApiResponse<GitHubUserResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUserDetail(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchSearchUserResult(username)
                

                if (response.totalCount?.equals(0) != false) {
                    emit(ApiResponse.Empty)
                } else if (response.items.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response.items))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchUserFollower(username: String): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUserFollower(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchUserFollowing(username: String): Flow<ApiResponse<List<GitHubUserResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUserFollowing(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }
}
