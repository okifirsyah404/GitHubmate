package com.okifirsyah.githubmate.presentation.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.databinding.FragmentFavoriteBinding
import com.okifirsyah.githubmate.presentation.adapter.FavoriteUserAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.presentation.decorator.ListRecyclerViewItemDivider
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModel()

    private val usersAdapter by lazy {
        FavoriteUserAdapter(
            onClick = {
                navigateToDetail(it)
            },
            onFavoriteClick = { username, isFavorite ->
                onUnFavoriteTap(username, isFavorite)
            }
        )

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.mainToolbar.apply {
            title = getString(R.string.favorite)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
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
    }

    override fun initProcess() {
        viewModel.getFavoriteUser()
    }

    override fun initObservers() {
        viewModel.userResult.observe(viewLifecycleOwner) { response ->

            when (response) {
                is ApiResponse.Loading -> {
                    showEmpty(false)
                    showLoading(true)
                }

                is ApiResponse.Success -> {
                    showEmpty(false)
                    showLoading(false)
                    usersAdapter.setItems(ArrayList(response.data))
                }

                is ApiResponse.Error -> {
                    showEmpty(false)
                    showLoading(false)
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT)
                        .show()

                }

                is ApiResponse.Empty -> {
                    showEmpty(true)
                    showLoading(false)
                }

                else -> {
                    showLoading(false)
                    showEmpty(false)
                }
            }

        }
    }

    private fun showEmpty(isEmpty: Boolean) {
        binding.layoutEmpty.apply {
            if (isEmpty) {
                binding.rvUser.gone()
                tvEmptyMessage.text = getString(R.string.empty_favorite_list)
                root.show()
            } else {
                binding.rvUser.show()
                root.gone()
            }

        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                rvUser.gone()
                shimmerFavorite.startShimmer()
                shimmerFavorite.showShimmer(true)
                shimmerFavorite.show()

            }
        } else {
            binding.apply {
                shimmerFavorite.stopShimmer()
                shimmerFavorite.showShimmer(false)
                shimmerFavorite.gone()
                rvUser.show()
            }
        }
    }

    private fun navigateToDetail(username: String) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailUserFragment(username)
        findNavController().navigate(action)
    }

    private fun onUnFavoriteTap(username: String, isFavorite: Boolean) {

        viewModel.setFavoriteUser(username, isFavorite)

        if (usersAdapter.itemCount == 0) {
            showEmpty(true)
        }
    }

}



