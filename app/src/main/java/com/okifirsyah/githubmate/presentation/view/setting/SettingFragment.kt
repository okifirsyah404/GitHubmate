package com.okifirsyah.githubmate.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.databinding.FragmentSettingBinding
import com.okifirsyah.githubmate.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val viewModel: SettingViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.mainToolbar.apply {
            title = getString(R.string.setting)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initUI() {

    }

    override fun initProcess() {

    }

    override fun initObservers() {
        viewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean? ->
            binding.apply {
                if (isDarkModeActive != null) {
                    when (isDarkModeActive) {
                        true -> {
                            btnDarkTheme.isChecked = true
                        }

                        false -> {
                            btnLightTheme.isChecked = true
                        }
                    }
                } else {
                    btnSystemTheme.isChecked = true
                }
            }
        }
    }


    override fun initActions() {
        binding.apply {
            btnSystemTheme.setOnClickListener {
                viewModel.clearThemeSetting()
            }
            btnDarkTheme.setOnClickListener {
                viewModel.saveThemeSetting(true)
            }
            btnLightTheme.setOnClickListener {
                viewModel.saveThemeSetting(false)
            }
        }
    }

}
