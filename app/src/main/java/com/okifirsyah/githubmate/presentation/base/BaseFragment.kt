package com.okifirsyah.githubmate.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, savedInstanceState)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgument()
        initAppBar()
        initIntent()
        initUI()
        initActions()
        initProcess()
        initObservers()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        onClose()
        _binding = null
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB


    abstract fun initUI()
    
    abstract fun initObservers()
    protected open fun initProcess() {}

    protected open fun initArgument() {}

    protected open fun initAppBar() {}

    protected open fun initIntent() {}

    protected open fun initActions() {}

    protected open fun showLoading(isLoading: Boolean) {}

    protected open fun showError(isError: Boolean, message: String = "") {}

    protected open fun onRefresh() {}

    protected open fun onClose() {}

}
