package com.okifirsyah.githubmate.presentation.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.local.preference.StoragePreference
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.launch

class SettingViewModel(
    private val pref: StoragePreference,
    private val useCase: GitHubUserUseCaseImpl
) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean?> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun clearThemeSetting() {
        viewModelScope.launch {
            pref.clearThemeSetting()
        }
    }

}
