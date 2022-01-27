package com.hussien.quoty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hussien.quoty.R
import com.hussien.quoty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // on pre draw to be able to access nav controller without any crashes
        binding.root.doOnPreDraw {
            val navController = findNavController(R.id.nav_host_fragment)
            binding.toolbar.setupWithNavController(
                navController,
                AppBarConfiguration(navController.graph)
            )
        }
    }
}