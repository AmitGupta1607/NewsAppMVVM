package com.example.newsappmvvm.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(getString(R.string.top_headlines))

        setFABIconForCurrentMode()

        binding.fab.setOnClickListener { view ->

            val currentMode = AppCompatDelegate.getDefaultNightMode();
            if(Build.VERSION.SDK_INT>=29) {
                if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.fab.setImageResource(R.drawable.night_mode_white)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.fab.setImageResource(R.drawable.day_mode)
                }
            }
            else{
                binding.fab.visibility= View.GONE
            }
        }
    }

    private fun setFABIconForCurrentMode(){
        val currentMode = AppCompatDelegate.getDefaultNightMode();
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.fab.setImageResource(R.drawable.day_mode)
        } else {
            binding.fab.setImageResource(R.drawable.night_mode_white)
        }
    }




}