package com.okifirsyah.githubmate.presentation.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.launch

class FavoriteViewModel(private val useCase: GitHubUserUseCaseImpl) : ViewModel() {

    val userResult: LiveData<ApiResponse<List<GitHubUser>>> by lazy { _userResult }
    private val _userResult = MutableLiveData<ApiResponse<List<GitHubUser>>>()


    fun getFavoriteUser() {
        viewModelScope.launch {
            useCase.getFavoriteUser().collect {
                _userResult.value = it
            }
        }
    }

    fun setFavoriteUser(username: String, isFavorite: Boolean) {
        viewModelScope.launch {
            useCase.setFavoriteUser(username, isFavorite)
        }
    }

}
