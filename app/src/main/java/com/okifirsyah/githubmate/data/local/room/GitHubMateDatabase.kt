package com.okifirsyah.githubmate.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okifirsyah.githubmate.data.local.dao.GitHubUserDao
import com.okifirsyah.githubmate.data.model.GitHubUser

@Database(entities = [GitHubUser::class], version = 6, exportSchema = false)
abstract class GitHubMateDatabase : RoomDatabase() {
    abstract fun gitHubUserDao(): GitHubUserDao

}
