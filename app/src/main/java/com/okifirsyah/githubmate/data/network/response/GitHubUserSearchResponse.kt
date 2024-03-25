package com.okifirsyah.githubmate.data.network.response

import com.google.gson.annotations.SerializedName

data class GitHubUserSearchResponse(
    @SerializedName("total_count")
    val totalCount: Int?,
    val items: List<GitHubUserResponse>?,
)
