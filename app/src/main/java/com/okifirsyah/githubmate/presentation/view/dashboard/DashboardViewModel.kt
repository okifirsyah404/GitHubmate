package com.okifirsyah.githubmate.presentation.view.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.use_case.GitHubUserUseCaseImpl
import kotlinx.coroutines.launch

class DashboardViewModel(private val useCase: GitHubUserUseCaseImpl) : ViewModel() {

    val dashboardDataResult: LiveData<ApiResponse<DashboardDto>> by lazy { _dashboardDataResult }
    private val _dashboardDataResult = MutableLiveData<ApiResponse<DashboardDto>>()


    fun getUsers() {
        viewModelScope.launch {
            useCase.getDashboardData().collect {
                _dashboardDataResult.value = it
            }
        }
    }
    
}
