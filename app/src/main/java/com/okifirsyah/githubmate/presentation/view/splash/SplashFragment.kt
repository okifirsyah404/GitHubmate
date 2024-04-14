package com.okifirsyah.githubmate.presentation.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.okifirsyah.githubmate.BuildConfig
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.databinding.FragmentSplashBinding
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import com.okifirsyah.githubmate.resource.constant.DurationConstant
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    private fun initLoading() {
        val handler = Handler(Looper.getMainLooper())
        val navigateToDashboardRunnable = Runnable {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    findNavController().navigate(R.id.action_splashFragment_to_dashboardFragment)
                }
            }
        }

        handler.postDelayed(navigateToDashboardRunnable, DurationConstant.SPLASH_DURATION)
    }

    override fun initUI() {
        binding.tvSplashVersion.text = "v ${BuildConfig.VERSION_NAME}"
    }

    override fun initProcess() {
        initLoading()
    }

    override fun initObservers() {}
}







