package com.eulsapet.nogleproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eulsapet.nogleproject.R
import com.eulsapet.nogleproject.databinding.FragmentABinding
import com.eulsapet.nogleproject.repository.BaseCallBackStatus
import com.eulsapet.nogleproject.repository.FragmentARepository
import com.eulsapet.nogleproject.view.adapter.MarketListAdapter
import com.eulsapet.nogleproject.viewmodel.FragmentAViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentA: Fragment() {
    /**
     * ViewBinding
     */
    private var _binding: FragmentABinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel
     */
    private val viewModel by viewModels<FragmentAViewModel> {
        viewModelFactory {
            initializer {
                FragmentAViewModel(
                    FragmentARepository()
                )
            }
        }
    }

    /**
     * Adapter
     */
    private val markListAdapter: MarketListAdapter by lazy {
        MarketListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            adapter = markListAdapter
        }
        initView()
        viewModel.getMarketList()
        observe()
    }

    private fun initView() {
        binding.rgGroup.setOnCheckedChangeListener { _, _ -> observe() }
    }

    private fun observe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest {
                    when (it) {
                        is BaseCallBackStatus.LOADING -> {
                            binding.pbLoading.visibility = if (it.loading) View.VISIBLE else View.GONE
                        }

                        is BaseCallBackStatus.SUCCESS -> {
                            binding.pbLoading.visibility = View.GONE
                            if (it.data.data.isNotEmpty()) {
                                if (binding.rbSpot.isChecked) {
                                    // 現貨
                                    val spotData = it.data.data.filter { tab -> !tab.future }
                                    markListAdapter.submitList(
                                        spotData.sortedBy { data ->
                                            data.marketName
                                        })
                                } else {
                                    // 期貨
                                    val spotData = it.data.data.filter { tab -> tab.future }
                                    markListAdapter.submitList(
                                        spotData.sortedBy { data ->
                                            data.marketName
                                        })
                                }
                            }
                        }

                        is BaseCallBackStatus.ERROR -> {
                            binding.pbLoading.visibility = View.GONE
                            Snackbar.make(binding.root, R.string.api_error_msg, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}