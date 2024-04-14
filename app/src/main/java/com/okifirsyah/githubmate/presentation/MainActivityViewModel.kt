package com.okifirsyah.githubmate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.okifirsyah.githubmate.data.local.preference.StoragePreference

class MainActivityViewModel(private val pref: StoragePreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean?> {
        return pref.getThemeSetting().asLiveData()
    }
}
