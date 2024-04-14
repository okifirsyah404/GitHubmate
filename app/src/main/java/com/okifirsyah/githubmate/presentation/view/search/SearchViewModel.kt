package com.okifirsyah.githubmate.presentation.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SearchViewModel(private val useCase: GitHubUserUseCaseImpl) : ViewModel() {

    val gitHubUserSearchResult: LiveData<ApiResponse<List<GitHubUser>>> by lazy { _gitHubUserSearchResult }
    private val _gitHubUserSearchResult = MutableLiveData<ApiResponse<List<GitHubUser>>>()

    fun searchUser(username: String) {
        viewModelScope.launch {
            useCase.getSearchUserResult(username).collect {
                _gitHubUserSearchResult.value = it
            }
        }
    }

    fun cancel() {
        viewModelScope.cancel()
    }

}
