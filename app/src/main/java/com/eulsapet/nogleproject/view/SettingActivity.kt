package com.eulsapet.nogleproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eulsapet.nogleproject.databinding.ActivitySettingBinding

class SettingActivity: AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        binding.apply {
            lifecycleOwner = this@SettingActivity
        }

        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.ibSetting.setOnClickListener {
            this.finish()
        }
    }
}