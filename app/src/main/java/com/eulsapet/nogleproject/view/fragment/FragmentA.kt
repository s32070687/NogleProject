package com.eulsapet.nogleproject.view.fragment

import android.os.Bundle
import android.util.Log
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
        binding.rbSpot.setOnClickListener {
            binding.rbSpot.isChecked = true
            binding.rbFutures.isChecked = false
        }

        binding.rbFutures.setOnClickListener {
            binding.rbFutures.isChecked = true
            binding.rbSpot.isChecked = false
        }
    }

    /**
     *  observe
     */
    private fun observe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest {
                    binding.pbLoading.visibility = View.GONE
                    if (it.msg.isNotEmpty()) {
                        markListAdapter.submitList(it.data.sortedBy { data -> data.marketName })
//                        Snackbar.make(binding.root, R.string.api_error, Snackbar.LENGTH_SHORT)
//                            .show()
                    } else Log.e("Jason","error")
//                        Snackbar.make(binding.root, R.string.api_error, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}