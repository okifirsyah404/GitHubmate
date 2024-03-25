package com.okifirsyah.githubmate.presentation.view.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.databinding.FragmentFollowerBinding
import com.okifirsyah.githubmate.presentation.adapter.DetailPagerAdapter
import com.okifirsyah.githubmate.presentation.adapter.UserFollowerAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.presentation.view.detail_user.DetailUserFragmentDirections
import com.okifirsyah.githubmate.presentation.view.detail_user.DetailUserViewModel
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerFragment : BaseFragment<FragmentFollowerBinding>() {

    private val viewModel: FollowerViewModel by viewModel<FollowerViewModel>()
    private val parentViewModel: DetailUserViewModel by activityViewModel<DetailUserViewModel>()

    private val usersAdapter: UserFollowerAdapter by lazy {
        UserFollowerAdapter(
            onClick = {
                val navDirection = DetailUserFragmentDirections.actionDetailUserFragmentSelf(it)
                findNavController().navigate(navDirection)
            }
        )
    }

    private var username = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFollowerBinding {
        return FragmentFollowerBinding.inflate(inflater, container, false)
    }

    override fun initArgument() {
        if (arguments != null) {
            val name = arguments?.getString(DetailPagerAdapter.EXTRA_USERNAME)
            if (name != null) {
                username = name
            }
        }
    }

    override fun initUI() {

        binding.rvUser.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

    }

    override fun initProcess() {
        viewModel.getUserFollower(username)
    }

    override fun initObservers() {
        viewModel.userFollowerResult.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                    showError(false)
                }

                is ApiResponse.Success<List<GitHubUserResponse>> -> {
                    usersAdapter.setItems(ArrayList(res.data))
                    showLoading(false)
                    showError(false)
                }

                is ApiResponse.Error -> {
                    showLoading(false)
                    showError(true, res.errorMessage)
                }

                else -> {
                    showLoading(false)
                    showError(false)
                }

            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerLoading.startShimmer()
            binding.shimmerLoading.showShimmer(true)
            binding.shimmerLoading.show()
            binding.rvUser.gone()
        } else {
            binding.shimmerLoading.stopShimmer()
            binding.shimmerLoading.showShimmer(false)
            binding.shimmerLoading.gone()
            binding.rvUser.show()
        }
    }

    private fun showError(isError: Boolean, message: String = "") {
        if (isError) {
            binding.apply {
                layoutError.tvErrorMessage.text = message
                rvUser.gone()
                layoutError.root.show()
            }
        } else {
            binding.apply {
                rvUser.show()
                layoutError.root.gone()
            }
        }
    }

    override fun initActions() {
        binding.layoutError.btnRetry.setOnClickListener {
            viewModel.getUserFollower(username)
            parentViewModel.getDetailUser(username)
        }
    }

}


