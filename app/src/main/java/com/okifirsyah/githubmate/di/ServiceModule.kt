package com.okifirsyah.githubmate.di

import com.okifirsyah.githubmate.data.network.service.GitHubUserService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { provideGitHubApi(get()) }
}

private fun provideGitHubApi(retrofit: Retrofit): GitHubUserService {
    return retrofit.create(GitHubUserService::class.java)
}

