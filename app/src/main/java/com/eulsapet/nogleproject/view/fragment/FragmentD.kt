package com.eulsapet.nogleproject.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.eulsapet.nogleproject.R
import com.eulsapet.nogleproject.databinding.FragmentDBinding

class FragmentD: Fragment() {
    /**
     * ViewBinding
     */
    private var _binding: FragmentDBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 前往設定頁
        binding.ibSetting.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fg_d_to_fg_setting)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}