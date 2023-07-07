package com.eulsapet.nogleproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.eulsapet.nogleproject.R
import com.eulsapet.nogleproject.databinding.ActivityMainBinding
import com.eulsapet.nogleproject.repository.MainRepository
import com.eulsapet.nogleproject.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * ViewModel
     */
    private val viewModel by viewModels<MainViewModel> {
        viewModelFactory {
            initializer {
                MainViewModel(
                    MainRepository()
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }

        setContentView(binding.root)
        initView()
        observe()
        viewModel.getMarketList()
    }

    /**
     *  init BottomNavigationView
     */
    private fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fgMain.id) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bnvMain, navHostFragment.findNavController())
    }

    /**
     *  observe
     */
    private fun observe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest {
                    Log.e("Jason","$it cc")
                    binding.pbLoading.visibility = View.GONE
                    if (it.msg.isNotEmpty()) {
                        Snackbar.make(binding.root, R.string.api_error, Snackbar.LENGTH_SHORT)
                            .show()
                    } else Snackbar.make(binding.root, R.string.api_error, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}