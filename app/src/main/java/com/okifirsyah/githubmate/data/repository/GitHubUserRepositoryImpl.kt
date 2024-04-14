package com.okifirsyah.githubmate.data.repository

import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.local.dao.GitHubUserDao
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.service.GitHubUserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GitHubUserRepositoryImpl(
    private val service: GitHubUserService,
    private val dao: GitHubUserDao
) : GitHubUserRepository() {

    override fun fetchDashboardData(): Flow<ApiResponse<DashboardDto>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val authorizedUserResponse = service.fetchauthorizedUser()

                if (dao.isUserIsExist(authorizedUserResponse.username)) {
                    dao.updateauthorizedUsers(false)
                    dao.updateauthorizedUser(authorizedUserResponse.id, true)
                } else {
                    authorizedUserResponse.isAuthorized = true
                    dao.insertauthorizedUser(authorizedUserResponse)
                }

                val authorizedUser = dao.getAuthorizedUser()

                val usersResponse = service.fetchUsers()
                dao.insertAllUsers(usersResponse)
                val users = dao.getAllUsers()

                emit(ApiResponse.Success(DashboardDto(authorizedUser, users)))
            } catch (e: Exception) {
                Timber.tag("GitHubUserRepositoryImpl").e(e)
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchauthorizedUser(): Flow<ApiResponse<GitHubUser>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchauthorizedUser()

                if (dao.isUserIsExist(response.username)) {
                    dao.updateauthorizedUsers(false)
                    dao.updateauthorizedUser(response.id, true)
                } else {
                    response.isAuthorized = true
                    dao.insertauthorizedUser(response)
                }

                val user = dao.getAuthorizedUser()

                emit(ApiResponse.Success(user))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchUsers(): Flow<ApiResponse<List<GitHubUser>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUsers()

                dao.insertAllUsers(response)
                val users = dao.getAllUsers()

                emit(ApiResponse.Success(users))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchUserDetail(username: String): Flow<ApiResponse<GitHubUser>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUserDetail(username)

                if (dao.isUserIsExist(response.username)) {
                    dao.updateUser(
                        response.username,
                        response.name ?: "-",
                        response.company ?: "-",
                        response.location ?: "-",
                        response.followers ?: 0,
                        response.following ?: 0
                    )
                } else {
                    dao.insertUser(response)
                }

                val user = dao.getDetailUser(username)

                emit(ApiResponse.Success(user))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchSearchUserResult(username: String): Flow<ApiResponse<List<GitHubUser>>> {
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

    override fun fetchUserFollower(username: String): Flow<ApiResponse<List<GitHubUser>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUserFollower(username)

                if (response.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchUserFollowing(username: String): Flow<ApiResponse<List<GitHubUser>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchUserFollowing(username)
                if (response.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override fun fetchFavoriteUsers(): Flow<ApiResponse<List<GitHubUser>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val users = dao.getAllFavoriteUsers()
                if (users.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(users))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    override suspend fun updateUserFavorite(username: String, isFavorite: Boolean): Unit {
        dao.updateUserFavoriteStatus(username, isFavorite)
    }

}
