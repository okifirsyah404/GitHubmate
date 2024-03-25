package com.okifirsyah.githubmate.di

import com.okifirsyah.githubmate.presentation.view.dashboard.DashboardViewModel
import com.okifirsyah.githubmate.presentation.view.detail_user.DetailUserViewModel
import com.okifirsyah.githubmate.presentation.view.follower.FollowerViewModel
import com.okifirsyah.githubmate.presentation.view.following.FollowingViewModel
import com.okifirsyah.githubmate.presentation.view.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        DashboardViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        DetailUserViewModel(get())
    }
    viewModel {
        FollowerViewModel(get())
    }
    viewModel {
        FollowingViewModel(get())
    }
}
