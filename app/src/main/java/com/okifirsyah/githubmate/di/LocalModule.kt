package com.okifirsyah.githubmate.di

import android.app.Application
import androidx.room.Room
import com.okifirsyah.githubmate.BuildConfig
import com.okifirsyah.githubmate.data.local.room.GitHubMateDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    factory { get<GitHubMateDatabase>().gitHubUserDao() }

    fun provideDatabase(application: Application): GitHubMateDatabase {
        return Room.databaseBuilder(
            application,
            GitHubMateDatabase::class.java,
            BuildConfig.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}
