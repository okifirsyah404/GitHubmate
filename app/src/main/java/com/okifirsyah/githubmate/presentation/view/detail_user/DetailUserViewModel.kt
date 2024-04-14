package com.okifirsyah.githubmate.presentation.view.detail_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.launch

class DetailUserViewModel(private val useCase: GitHubUserUseCaseImpl) : ViewModel() {

    val detailUserResult: LiveData<ApiResponse<GitHubUser>> by lazy { _detailUserResultResult }
    private val _detailUserResultResult = MutableLiveData<ApiResponse<GitHubUser>>()

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            useCase.getUserDetail(username).collect {
                _detailUserResultResult.value = it
            }
        }
    }

    fun setFavoriteUser(username: String, isFavorite: Boolean) {
        viewModelScope.launch {
            useCase.setFavoriteUser(username, isFavorite)
        }
    }

}
