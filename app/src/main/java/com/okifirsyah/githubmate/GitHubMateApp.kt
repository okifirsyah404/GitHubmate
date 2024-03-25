package com.okifirsyah.githubmate

import android.app.Application
import com.okifirsyah.githubmate.di.networkModule
import com.okifirsyah.githubmate.di.repositoryModule
import com.okifirsyah.githubmate.di.serviceModule
import com.okifirsyah.githubmate.di.useCaseModule
import com.okifirsyah.githubmate.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class GitHubMateApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GitHubMateApp)

            modules(
                listOf(
                    networkModule,
                    serviceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}
