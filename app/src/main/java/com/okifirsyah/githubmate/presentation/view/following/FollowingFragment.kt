package com.okifirsyah.githubmate.presentation.view.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.databinding.FragmentFollowingBinding
import com.okifirsyah.githubmate.presentation.adapter.DetailPagerAdapter
import com.okifirsyah.githubmate.presentation.adapter.UserFollowingAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.presentation.decorator.ListRecyclerViewItemDivider
import com.okifirsyah.githubmate.presentation.view.detail_user.DetailUserFragmentDirections
import com.okifirsyah.githubmate.presentation.view.detail_user.DetailUserViewModel
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : BaseFragment<FragmentFollowingBinding>() {


    private val viewModel: FollowingViewModel by viewModel<FollowingViewModel>()
    private val parentViewModel: DetailUserViewModel by activityViewModel<DetailUserViewModel>()

    private val userAdapter: UserFollowingAdapter by lazy {
        UserFollowingAdapter(
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
    ): FragmentFollowingBinding {
        return FragmentFollowingBinding.inflate(inflater, container, false)
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
            adapter = userAdapter

            addItemDecoration(
                ListRecyclerViewItemDivider(
                    resources.getDimension(R.dimen.dimen_8dp).toInt(),
                    resources.getDimension(R.dimen.dimen_16dp).toInt()
                )
            )
        }
    }

    override fun initActions() {
        binding.layoutError.btnRetry.setOnClickListener {
            viewModel.getUserFollowing(username)
            parentViewModel.getDetailUser(username)
        }
    }

    override fun initProcess() {
        viewModel.getUserFollowing(username)
    }


    override fun initObservers() {
        viewModel.userFollowingResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                    showError(false)
                    showEmpty(false)
                }

                is ApiResponse.Success -> {
                    userAdapter.setItems(ArrayList(response.data))
                    showLoading(false)
                    showEmpty(false)
                    showError(false)
                }

                is ApiResponse.Error -> {
                    showLoading(false)
                    showEmpty(false)
                    showError(true, response.errorMessage)
                }

                is ApiResponse.Empty -> {
                    showLoading(false)
                    showError(false)
                    showEmpty(true)
                }

                else -> {
                    showLoading(false)
                    showError(false)
                    showEmpty(false)
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

    override fun showError(isError: Boolean, message: String) {
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

    private fun showEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.apply {
                layoutEmpty.tvEmptyMessage.text = getString(R.string.empty_following_list)
                rvUser.gone()
                layoutEmpty.root.show()
            }
        } else {
            binding.apply {
                rvUser.show()
                layoutEmpty.root.gone()
            }
        }
    }


}

