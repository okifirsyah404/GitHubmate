package com.okifirsyah.githubmate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class GitHubUser(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("login")
    @ColumnInfo(name = "username")
    val username: String,
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarURL: String,
    val url: String?,
    val name: String?,
    val company: String?,
    val location: String?,
    val followers: Int?,
    val following: Int?,
    @ColumnInfo("is_favorite")
    var isFavorite: Boolean = false,
    @ColumnInfo("is_authorized")
    var isAuthorized: Boolean = false,
)
