package com.okifirsyah.githubmate.di

import com.okifirsyah.githubmate.data.repository.GitHubUserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { GitHubUserRepositoryImpl(get(), get()) }
}


