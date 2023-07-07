package com.eulsapet.nogleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eulsapet.nogleproject.repository.FragmentARepository
import com.eulsapet.nogleproject.repository.model.MarketList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FragmentAViewModel(
    private val mainRepository: FragmentARepository
): ViewModel() {

    private val _data = MutableStateFlow(MarketList())

    val data: StateFlow<MarketList> = _data

    fun getMarketList() {
        viewModelScope.launch {
            _data.value = mainRepository.getMarketList()
        }
    }
}