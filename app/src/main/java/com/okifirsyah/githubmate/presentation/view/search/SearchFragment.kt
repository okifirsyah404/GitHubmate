package com.okifirsyah.githubmate.presentation.view.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.databinding.FragmentSearchBinding
import com.okifirsyah.githubmate.presentation.adapter.SearchUserAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.presentation.decorator.ListRecyclerViewItemDivider
import com.okifirsyah.githubmate.resource.constant.DurationConstant
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by inject()
    private val usersAdapter: SearchUserAdapter by lazy {
        SearchUserAdapter {
            navigateToDetailUser(it)
        }
    }


    private var searchTask: Job? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.mainToolbar.apply {
            title = getString(R.string.search)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initUI() {
        binding.apply {
            rvUser.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = usersAdapter
                addItemDecoration(
                    ListRecyclerViewItemDivider(
                        resources.getDimension(R.dimen.dimen_8dp).toInt(),
                        resources.getDimension(R.dimen.dimen_16dp).toInt()
                    )
                )
            }

            rvSearchUser.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = usersAdapter
                addItemDecoration(
                    ListRecyclerViewItemDivider(
                        resources.getDimension(R.dimen.dimen_8dp).toInt(),
                        resources.getDimension(R.dimen.dimen_16dp).toInt()
                    )
                )
            }

            searchView.apply {
                setupWithSearchBar(searchBar)
            }
        }
    }

    override fun initActions() {
        binding.searchView.run {
            editText.setOnEditorActionListener { tv, _, _ ->
                this.setText(tv.text)

                viewModel.searchUser(tv.text.toString())

                this.clearFocusAndHideKeyboard()
                this.hide()

                true
            }

            editText.addTextChangedListener(object : TextWatcher {

                var text = ""

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    searchTask?.cancel()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    text = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    searchTask = CoroutineScope(Dispatchers.Main).launch {
                        delay(DurationConstant.SPLASH_ANIMATE_DURATION)

                        usersAdapter.clearItems()

                        if (text.isNotEmpty()) {
                            binding.searchBar.setText(text)
                            viewModel.searchUser(text)
                        }

                    }

                }
            })
        }
    }

    override fun initObservers() {
        viewModel.gitHubUserSearchResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                    showError(false)
                    showEmpty(false)
                }

                is ApiResponse.Success -> {
                    showLoading(false)
                    showError(false)
                    showEmpty(false)
                    onSuccessBind(ArrayList(response.data))
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
            binding.apply {
                rvSearchUser.gone()
                rvUser.gone()
                shimmerLoadingSearch.startShimmer()
                shimmerLoadingSearch.showShimmer(true)
                shimmerLoadingSearch.show()
                shimmerLoading.startShimmer()
                shimmerLoading.showShimmer(true)
                shimmerLoading.show()

            }
        } else {
            binding.apply {
                shimmerLoadingSearch.stopShimmer()
                shimmerLoadingSearch.showShimmer(false)
                shimmerLoadingSearch.gone()
                shimmerLoading.stopShimmer()
                shimmerLoading.showShimmer(false)
                shimmerLoading.gone()
                rvSearchUser.show()
                rvUser.show()
            }
        }
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            binding.apply {
                errorContainer.tvErrorMessage.text = message
                errorContainerSearch.tvErrorMessage.text = message

                errorContainerSearch.root.show()
                errorContainer.root.show()

                errorContainer.btnRetry.setOnClickListener {
                    viewModel.searchUser(searchBar.text.toString())
                }

                errorContainerSearch.btnRetry.setOnClickListener {
                    viewModel.searchUser(searchBar.text.toString())
                }

                rvSearchUser.gone()
                rvUser.gone()
            }
        } else {
            binding.apply {
                errorContainer.root.gone()
                errorContainerSearch.root.gone()
            }
        }
    }

    private fun onSuccessBind(data: ArrayList<GitHubUser>) {

        Timber.tag("SearchFragment").d("data $data")

        if (data.isEmpty()) {
            showEmpty(true)
            return
        }

        usersAdapter.setItems(data)
        binding.apply {
            rvSearchUser.show()
            rvUser.show()
        }
    }

    private fun showEmpty(isEmpty: Boolean) {
        binding.apply {
            if (isEmpty) {
                binding.rvUser.gone()
                emptyContainerSearch.tvEmptyMessage.text = getString(R.string.empty_search_list)
                emptyContainerSearch.root.show()
                emptyContainer.tvEmptyMessage.text = getString(R.string.empty_search_list)
                emptyContainer.root.show()
            } else {
                binding.rvUser.show()
                emptyContainerSearch.root.gone()
                emptyContainer.root.gone()
            }
        }
    }

    private fun navigateToDetailUser(username: String) {
        if (searchTask != null) {
            searchTask?.cancel()
        }

        val navDirection =
            SearchFragmentDirections.actionSearchFragmentToDetailUserFragment(username)
        findNavController().navigate(navDirection)
    }


    override fun onClose() {
        super.onClose()
        if (searchTask != null) {
            searchTask?.cancel()
        }
    }

}

