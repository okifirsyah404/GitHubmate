package com.okifirsyah.githubmate.presentation.view.detail_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.databinding.FragmentDetailUserBinding
import com.okifirsyah.githubmate.presentation.adapter.DetailPagerAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber

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
                0 -> getString(R.string.followers)
                else -> getString(R.string.following)
            }
        }.attach()
    }


    override fun initProcess() {
        viewModel.getDetailUser(username)
    }

    override fun initObservers() {
        viewModel.detailUserResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                    showError(false)
                }

                is ApiResponse.Success -> {
                    onSuccessBind(response.data)
                    showError(false)
                    showLoading(false)
                }

                is ApiResponse.Error -> {
                    showError(true)
                    showLoading(false)
                }

                else -> {
                    showError(false)
                    showLoading(false)
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

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            binding.apply {
                civAvatar.load(R.drawable.github_logo)
            }
        }
    }

    private fun onSuccessBind(data: GitHubUser) {
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


        binding.btnFavorite.apply {
            fun setBtnFavorite() {
                if (data.isFavorite) {
                    text = getString(R.string.un_favorite)
                    setIconResource(R.drawable.ic_favorite_fill)
                } else {
                    text = getString(R.string.favorite)
                    setIconResource(R.drawable.ic_favorite_outlined)
                }
            }

            setBtnFavorite()

            setOnClickListener {
                data.isFavorite = !data.isFavorite
                setBtnFavorite()
                Timber.tag("DetailUserFragment").d("isFavorite: ${data.isFavorite}")

                viewModel.setFavoriteUser(
                    data.username,
                    data.isFavorite
                )
            }
        }


        val followingCount = "${getString(R.string.following)} (${data.following})"
        val followersCount = "${getString(R.string.followers)} (${data.followers})"

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> followersCount
                else -> followingCount
            }
        }.attach()
    }

}

