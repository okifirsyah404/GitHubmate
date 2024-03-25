package com.okifirsyah.githubmate.data.dto

import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse

data class DashboardDto (
    val authorizedUser: GitHubUserResponse? = null,
    val users: List<GitHubUserResponse>? = null
)
