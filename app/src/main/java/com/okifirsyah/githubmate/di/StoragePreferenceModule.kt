package com.okifirsyah.githubmate.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.okifirsyah.githubmate.data.local.preference.StoragePreference
import org.koin.dsl.module

fun storagePreferenceModule(pref: DataStore<Preferences>) = module {
    single {
        pref
    }

    single {
        StoragePreference.getInstance(get())
    }
}
