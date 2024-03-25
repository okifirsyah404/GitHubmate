package com.okifirsyah.githubmate.di

import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory { GitHubUserUseCaseImpl(get()) }
}
