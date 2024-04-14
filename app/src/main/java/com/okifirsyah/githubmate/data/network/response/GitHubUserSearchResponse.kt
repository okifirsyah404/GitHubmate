package com.okifirsyah.githubmate.data.network.response

import com.google.gson.annotations.SerializedName
import com.okifirsyah.githubmate.data.model.GitHubUser

data class GitHubUserSearchResponse(
    @SerializedName("total_count")
    val totalCount: Int?,
    val items: List<GitHubUser>?,
)
