package com.okifirsyah.githubmate.presentation.view.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.launch

class FollowerViewModel(private val useCase: GitHubUserUseCaseImpl) : ViewModel() {

    val userFollowerResult: LiveData<ApiResponse<List<GitHubUserResponse>>> by lazy { _userFollowerResult }
    private val _userFollowerResult = MutableLiveData<ApiResponse<List<GitHubUserResponse>>>()

    fun getUserFollower(username: String) {
        viewModelScope.launch {
            useCase.getUserFollower(username).collect {
                _userFollowerResult.postValue(it)
            }
        }
    }


}
