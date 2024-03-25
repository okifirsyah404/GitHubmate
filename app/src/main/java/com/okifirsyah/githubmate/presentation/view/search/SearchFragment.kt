package com.okifirsyah.githubmate.presentation.view.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.network.ApiResponse
import com.okifirsyah.githubmate.databinding.FragmentSearchBinding
import com.okifirsyah.githubmate.presentation.adapter.GitHubUsersAdapter
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.presentation.decorator.ListRecyclerViewItemDivider
import com.okifirsyah.githubmate.utils.extension.gone
import com.okifirsyah.githubmate.utils.extension.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by inject()
    private val usersAdapter: GitHubUsersAdapter by lazy {
        GitHubUsersAdapter(
            onClick = {

                if (searchTask != null) {
                    searchTask?.cancel()
                }

                val navDirection =
                    SearchFragmentDirections.actionSearchFragmentToDetailUserFragment(it)
                findNavController().navigate(navDirection)
            }
        )
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
            }

            rvSearchUser.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = usersAdapter
                addItemDecoration(ListRecyclerViewItemDivider(16, 16))
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

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    searchTask?.cancel()
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    // do nothing
                }

                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()

                    searchTask = CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)


                        if (text.isNotEmpty()) {
                            binding.searchBar.setText(text)
                            viewModel.searchUser(text)
                        } else {
                            usersAdapter.clearItems()
                        }
                    }

                }
            })
        }
        binding.apply {
            btnRetry.setOnClickListener {
                viewModel.searchUser(searchBar.text.toString())
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservers() {
        viewModel.gitHubUserSearchResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    showLoading(false)
                    usersAdapter.setItems(ArrayList(response.data))
                    binding.apply {
                        layoutError.gone()
                        searchLayoutError.gone()
                        rvSearchUser.show()
                        rvUser.show()
                    }
                }

                is ApiResponse.Error -> {
                    showLoading(false)
                    binding.apply {

                        tvErrorMessage.text = response.errorMessage
                        tvSearchErrorMessage.text = response.errorMessage

                        layoutError.show()
                        searchLayoutError.show()
                        rvSearchUser.gone()
                        rvUser.gone()
                    }
                }

                is ApiResponse.Loading -> {
                    showLoading(true)
                    binding.apply {
                        layoutError.gone()
                        searchLayoutError.gone()
                    }
                }

                else -> {
                    showLoading(false)
                    binding.apply {
                        layoutError.gone()
                        searchLayoutError.gone()
                    }
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

    override fun onClose() {
        super.onClose()
        if (searchTask != null) {
            searchTask?.cancel()
        }
    }

}

