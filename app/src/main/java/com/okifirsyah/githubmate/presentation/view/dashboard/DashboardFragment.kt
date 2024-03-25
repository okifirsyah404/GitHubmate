package com.okifirsyah.githubmate.presentation.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.databinding.FragmentDashboardBinding
import com.okifirsyah.githubmate.presentation.adapter.GitHubUsersAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel<DashboardViewModel>()
    private val usersAdapter: GitHubUsersAdapter by lazy {
        GitHubUsersAdapter(
            onClick = {
                val navDirection =
                    DashboardFragmentDirections.actionDashboardFragmentToDetailUserFragment(it)
                findNavController().navigate(navDirection)
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
        }

        onAppBarOffsetChanged()
    }

    override fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerDashboard.startShimmer()
                shimmerDashboard.showShimmer(true)
                shimmerDashboard.show()
                rvUser.gone()
                clDashboardHeader.gone()
            } else {
                shimmerDashboard.stopShimmer()
                shimmerDashboard.showShimmer(false)
                shimmerDashboard.gone()
                rvUser.show()
                clDashboardHeader.show()
            }
        }
    }

    override fun initProcess() {
        viewModel.getUsers()
    }

    override fun initObservers() {
        viewModel.dashboardDataResult.observe(viewLifecycleOwner) {
            when (it) {

                is ApiResponse.Loading -> {
                    showLoading(true)
                    binding.errorContainer.root.gone()
                }

                is ApiResponse.Success -> {
                    showLoading(false)

                    it.data.users?.let { it1 -> ArrayList(it1) }
                        ?.let { it2 -> usersAdapter.setItems(it2) }
                    binding.apply {
                        rvUser.show()
                        ivBackground.show()
                        clDashboardHeader.show()
                        errorContainer.root.gone()
                        btnSearch.isEnabled = true

                        tvAuthor.text = it.data.authorizedUser?.username
                        tvToolbarUsername.text = it.data.authorizedUser?.username
                        ivToolbarLogo.load(it.data.authorizedUser?.avatarURL) {
                            placeholder(R.drawable.github_logo)
                        }
                        ivLogo.load(it.data.authorizedUser?.avatarURL) {
                            placeholder(R.drawable.github_logo)
                        }
                    }
                }

                is ApiResponse.Error -> {
                    showLoading(false)
                    binding.apply {
                        rvUser.gone()
                        ivBackground.gone()
                        clDashboardHeader.gone()
                        errorContainer.root.show()

                        errorContainer.tvErrorMessage.text = it.errorMessage
                        btnSearch.isEnabled = false
                    }
                }

                else -> {
                    showLoading(false)
                    binding.errorContainer.root.gone()
                }
            }
        }

    }

    override fun initActions() {
        binding.apply {
            errorContainer.btnRetry.setOnClickListener {
                viewModel.getUsers()
            }
        }

    }

    override fun initIntent() {
        binding.apply {
            btnToolbarSearch.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
            }
            btnSearch.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
            }
        }
    }

    private fun onAppBarOffsetChanged() {
        binding.apply {
            dashboardAppBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                    ivToolbarLogo.show()
                    tvToolbarUsername.show()
                    btnToolbarSearch.show()
                } else if (verticalOffset == 0) {
                    ivToolbarLogo.gone()
                    tvToolbarUsername.gone()
                    btnToolbarSearch.gone()
                } else {
                    ivToolbarLogo.gone()
                    tvToolbarUsername.gone()
                    btnToolbarSearch.gone()
                }
            }
        }
    }

}

