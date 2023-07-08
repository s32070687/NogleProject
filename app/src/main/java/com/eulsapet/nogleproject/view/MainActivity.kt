package com.eulsapet.nogleproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.eulsapet.nogleproject.R
import com.eulsapet.nogleproject.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setMessage(resources.getString(R.string.msg_leave))
            .setNegativeButton(resources.getString(R.string.msg_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.msg_confirm)) { dialog, _ ->
                finish()
                dialog.dismiss()
            }
            .show()
    }
}