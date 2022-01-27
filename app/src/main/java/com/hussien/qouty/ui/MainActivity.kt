package com.hussien.qouty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.hussien.qouty.R
import com.hussien.qouty.databinding.ActivityMainBinding
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