package com.okifirsyah.githubmate.di

import com.okifirsyah.githubmate.presentation.MainActivityViewModel
import com.okifirsyah.githubmate.presentation.view.dashboard.DashboardViewModel
import com.okifirsyah.githubmate.presentation.view.detail_user.DetailUserViewModel
import com.okifirsyah.githubmate.presentation.view.favorite.FavoriteViewModel
import com.okifirsyah.githubmate.presentation.view.follower.FollowerViewModel
import com.okifirsyah.githubmate.presentation.view.following.FollowingViewModel
import com.okifirsyah.githubmate.presentation.view.search.SearchViewModel
import com.okifirsyah.githubmate.presentation.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainActivityViewModel(get())
    }
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
    viewModel {
        SettingViewModel(get(), get())
    }
    viewModel {
        FavoriteViewModel(get())
    }
}
