package com.okifirsyah.githubmate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.okifirsyah.githubmate.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        viewModel.getThemeSettings().observe(this) { isDarkMode ->
            Timber.tag("MainActivity").d("isDarkMode: $isDarkMode")

            AppCompatDelegate.setDefaultNightMode(isDarkMode?.let {
                if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            } ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
