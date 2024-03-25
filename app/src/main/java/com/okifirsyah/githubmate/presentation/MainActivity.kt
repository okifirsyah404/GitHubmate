package com.okifirsyah.githubmate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okifirsyah.githubmate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
