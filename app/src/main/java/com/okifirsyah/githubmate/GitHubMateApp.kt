package com.okifirsyah.githubmate

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.okifirsyah.githubmate.di.localModule
import com.okifirsyah.githubmate.di.networkModule
import com.okifirsyah.githubmate.di.repositoryModule
import com.okifirsyah.githubmate.di.serviceModule
import com.okifirsyah.githubmate.di.storagePreferenceModule
import com.okifirsyah.githubmate.di.useCaseModule
import com.okifirsyah.githubmate.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class GitHubMateApp : Application() {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GitHubMateApp)

            modules(
                listOf(
                    storagePreferenceModule(dataStore),
                    localModule,
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
