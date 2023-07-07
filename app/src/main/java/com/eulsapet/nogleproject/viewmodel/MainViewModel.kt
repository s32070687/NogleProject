package com.eulsapet.nogleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eulsapet.nogleproject.repository.MainRepository
import com.eulsapet.nogleproject.repository.model.MarketList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _data = MutableStateFlow(MarketList())

    val data: StateFlow<MarketList> = _data

    fun getMarketList() {
        viewModelScope.launch {
            _data.value = mainRepository.getMarketList()
        }
    }
}