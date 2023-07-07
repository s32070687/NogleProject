package com.eulsapet.nogleproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.eulsapet.nogleproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }

        setContentView(binding.root)
        initView()
    }

    /**
     *  init BottomNavigationView
     */
    private fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fgMain.id) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bnvMain, navHostFragment.findNavController())
    }
}