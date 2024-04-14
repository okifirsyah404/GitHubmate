package com.okifirsyah.githubmate.presentation.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.dto.DashboardDto
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.databinding.FragmentDashboardBinding
import com.okifirsyah.githubmate.presentation.adapter.GitHubUsersAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.presentation.decorator.ListRecyclerViewItemDivider
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel<DashboardViewModel>()
    private val usersAdapter by lazy {
        GitHubUsersAdapter(
            onClick = {
                navigateToDetailUser(it)
            },
            onFavoriteClick = { username, isFavorite ->
                viewModel.setFavoriteUser(username, isFavorite)
            }
        )
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.rvUser.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = usersAdapter

            addItemDecoration(
                ListRecyclerViewItemDivider(
                    resources.getDimension(R.dimen.dimen_8dp).toInt(),
                    resources.getDimension(R.dimen.dimen_16dp).toInt()
                )
            )
        }

        onAppBarOffsetChanged()
    }

    override fun initProcess() {
        viewModel.getUsers()
    }

    override fun initActions() {
        binding.apply {
            errorContainer.btnRetry.setOnClickListener {
                viewModel.getUsers()
            }
        }
    }

    override fun initIntent() {
        binding.toolbarDashboard.apply {
            btnToolbarSearch.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
            }
            btnToolbarSetting.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_settingFragment)
            }
            btnToolbarFavorite.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_favoriteFragment)
            }
            btnSearch.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
            }
            btnSetting.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_settingFragment)
            }
            btnFavorite.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_favoriteFragment)
            }
        }
    }

    override fun initObservers() {
        viewModel.dashboardDataResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showError(false)
                    showLoading(true)
                }

                is ApiResponse.Success -> {
                    showLoading(false)
                    showError(false)
                    onSuccessBind(response.data)
                }

                is ApiResponse.Error -> {
                    showLoading(false)
                    showError(true, response.errorMessage)
                }

                else -> {
                    showLoading(false)
                    showError(false)
                }
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerDashboard.startShimmer()
                shimmerDashboard.showShimmer(true)
                shimmerDashboard.show()
                rvUser.gone()
                toolbarDashboard.clDashboardHeader.gone()
            } else {
                shimmerDashboard.stopShimmer()
                shimmerDashboard.showShimmer(false)
                shimmerDashboard.gone()
                rvUser.show()
                toolbarDashboard.clDashboardHeader.show()
            }
        }
    }

    override fun showError(isError: Boolean, message: String) {
        binding.apply {
            if (isError) {
                content.gone()
                errorContainer.root.show()
                errorContainer.tvErrorMessage.text = message

                toolbarDashboard.apply {
                    ivBackground.gone()
                    clDashboardHeader.gone()
                    btnSearch.isEnabled = false
                }
            } else {
                content.show()
                errorContainer.root.gone()
            }
        }

    }

    private fun onSuccessBind(data: DashboardDto) {
        data.users?.let { it1 -> ArrayList(it1) }
            ?.let { it2 -> usersAdapter.setItems(it2) }

        showAppBarElements()
        setAppBarUserInfo(
            username = data.authorizedUser?.username,
            avatarURL = data.authorizedUser?.avatarURL
        )
    }

    private fun showAppBarElements() {
        binding.toolbarDashboard.apply {
            ivBackground.show()
            clDashboardHeader.show()
            btnSearch.isEnabled = true
        }
    }

    private fun setAppBarUserInfo(username: String?, avatarURL: String?) {
        binding.toolbarDashboard.apply {
            tvAuthor.text = username
            tvToolbarUsername.text = username

            ivToolbarLogo.setOnClickListener {
                username?.let { navigateToDetailUser(it) }
            }
            ivLogo.setOnClickListener {
                username?.let { navigateToDetailUser(it) }
            }

            ivToolbarLogo.load(avatarURL) {
                placeholder(R.drawable.github_logo)
                error(R.drawable.github_logo)
            }
            ivLogo.load(avatarURL) {
                placeholder(R.drawable.github_logo)
                error(R.drawable.github_logo)
            }
        }
    }

    private fun onAppBarOffsetChanged() {
        binding.toolbarDashboard.apply {
            dashboardAppBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                    rlToolbarDashboard.show()
                } else if (verticalOffset == 0) {
                    rlToolbarDashboard.gone()
                } else {
                    rlToolbarDashboard.gone()
                }
            }
        }
    }

    private fun navigateToDetailUser(username: String) {
        val navDirection =
            DashboardFragmentDirections.actionDashboardFragmentToDetailUserFragment(username)
        findNavController().navigate(navDirection)
    }
}

