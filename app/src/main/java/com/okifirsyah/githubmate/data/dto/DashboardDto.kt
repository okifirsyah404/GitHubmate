package com.okifirsyah.githubmate.data.dto

import com.okifirsyah.githubmate.data.model.GitHubUser

data class DashboardDto(
    val authorizedUser: GitHubUser? = null,
    val users: List<GitHubUser>? = null
)
