package com.okifirsyah.githubmate.presentation.view.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.launch

class FollowingViewModel(private val useCase: GitHubUserUseCaseImpl) : ViewModel() {

    val userFollowingResult: LiveData<ApiResponse<List<GitHubUser>>> by lazy { _userFollowingResult }
    private val _userFollowingResult = MutableLiveData<ApiResponse<List<GitHubUser>>>()

    fun getUserFollowing(username: String) {
        viewModelScope.launch {
            useCase.getUserFollowing(username).collect {
                _userFollowingResult.postValue(it)
            }
        }
    }

}
