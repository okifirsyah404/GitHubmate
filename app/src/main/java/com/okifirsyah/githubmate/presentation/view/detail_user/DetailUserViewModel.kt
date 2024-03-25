package com.okifirsyah.githubmate.presentation.view.detail_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.launch

class DetailUserViewModel(private val useCase: GitHubUserUseCaseImpl) : ViewModel() {

    val detailUserResult: LiveData<ApiResponse<GitHubUserResponse>> by lazy { _detailUserResultResult }
    private val _detailUserResultResult = MutableLiveData<ApiResponse<GitHubUserResponse>>()

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            useCase.getUserDetail(username).collect {
                _detailUserResultResult.value = it
            }
        }
    }

    val userFollowingResult: LiveData<ApiResponse<List<GitHubUserResponse>>> by lazy { _userFollowingResult }
    private val _userFollowingResult = MutableLiveData<ApiResponse<List<GitHubUserResponse>>>()

    fun getUserFollowing(username: String) {
        viewModelScope.launch {
            useCase.getUserFollowing(username).collect {
                _userFollowingResult.postValue(it)
            }
        }
    }


}
