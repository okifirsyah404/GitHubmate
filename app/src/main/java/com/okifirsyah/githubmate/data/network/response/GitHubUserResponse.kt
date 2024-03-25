package com.okifirsyah.githubmate.data.network.response

import com.google.gson.annotations.SerializedName

data class GitHubUserResponse(
    val id: Long,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarURL: String,
    @SerializedName("gravatar_id")
    val gravatarID: String,
    val url: String,
    val type: String,
    val name: String,
    val company: String?,
    val blog: String,
    val location: String?,
    val email: String?,
    val bio: String?,
    @SerializedName("twitter_username")
    val twitterUsername: Any? = null,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    val followers: Int?,
    val following: Int?,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
