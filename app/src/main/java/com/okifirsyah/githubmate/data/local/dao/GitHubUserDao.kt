package com.okifirsyah.githubmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okifirsyah.githubmate.data.model.GitHubUser

@Dao
interface GitHubUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUsers(users: List<GitHubUser>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: GitHubUser)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertauthorizedUser(user: GitHubUser)

    @Query("SELECT * FROM user LIMIT :limit")
    suspend fun getAllUsers(limit: Int = 30): List<GitHubUser>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getDetailUser(username: String): GitHubUser

    @Query("SELECT * FROM user WHERE is_authorized = 1 LIMIT 1")
    suspend fun getAuthorizedUser(): GitHubUser

    @Query("SELECT * FROM user WHERE is_favorite = 1")
    suspend fun getAllFavoriteUsers(): List<GitHubUser>

    @Query("UPDATE user SET is_authorized = :isAuthorized")
    suspend fun updateauthorizedUsers(isAuthorized: Boolean)

    @Query("UPDATE user SET is_authorized = :isAuthorized WHERE id = :id")
    suspend fun updateauthorizedUser(id: Int, isAuthorized: Boolean)

    @Query("UPDATE user SET is_favorite = :isFav WHERE username = :username")
    suspend fun updateUserFavoriteStatus(username: String, isFav: Boolean)

    @Query("UPDATE user SET company = :company, location = :location, name = :realName, followers = :followers, following = :following WHERE username = :username")
    suspend fun updateUser(
        username: String,
        realName: String,
        company: String,
        location: String,
        followers: Int,
        following: Int,
    )

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    suspend fun isUserIsExist(username: String): Boolean
}
