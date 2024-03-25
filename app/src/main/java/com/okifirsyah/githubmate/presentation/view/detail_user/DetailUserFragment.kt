package com.okifirsyah.githubmate.presentation.view.detail_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.databinding.FragmentDetailUserBinding
import com.okifirsyah.githubmate.presentation.adapter.DetailPagerAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailUserFragment : BaseFragment<FragmentDetailUserBinding>() {

    private val viewModel: DetailUserViewModel by activityViewModel<DetailUserViewModel>()
    private var pagerAdapter: DetailPagerAdapter? = null

    private val username by lazy {
        DetailUserFragmentArgs.fromBundle(requireArguments()).username
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailUserBinding {
        return FragmentDetailUserBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.mainToolbar.apply {
            title = getString(R.string.detail_user)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initUI() {

        pagerAdapter = DetailPagerAdapter(requireActivity(), username)

        binding.apply {
            tvUsername.text = username
            viewPager.adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.following)
                else -> getString(R.string.followers)
            }
        }.attach()

    }

    private fun onSuccessBind(data: GitHubUserResponse) {
        binding.apply {
            tvRealName.text = data.name
            civAvatar.load(data.avatarURL) {

                val res = R.drawable.github_logo
                placeholder(res)
                error(res)

            }

            tvWork.text = data.company.isNullOrEmpty().let {
                if (it) {
                    "-"
                } else {
                    data.company
                }
            }

            tvLocation.text = data.location.isNullOrEmpty().let {
                if (it) {
                    "-"
                } else {
                    data.location.toString()
                }
            }
        }

        val followingCount = " (${data.following})"
        val followersCount = " (${data.followers})"

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.following) + followingCount
                else -> getString(R.string.followers) + followersCount
            }
        }.attach()
    }

    override fun initProcess() {
        viewModel.getDetailUser(username)
    }

    override fun initObservers() {
        viewModel.detailUserResult.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }

                is ApiResponse.Success -> {
                    onSuccessBind(it.data)
                    showLoading(false)
                }

                is ApiResponse.Error -> {
                    showError(true)
                    showLoading(false)
                }

                else -> {
                    showLoading(false)
                    binding.clContent.show()
                }
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerDetail.startShimmer()
                shimmerDetail.showShimmer(true)
                shimmerDetail.show()
                clContent.gone()
            } else {
                shimmerDetail.stopShimmer()
                shimmerDetail.showShimmer(false)
                shimmerDetail.gone()
                clContent.show()
            }
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.apply {
                civAvatar.load(R.drawable.github_logo)
            }
        }
    }

}

