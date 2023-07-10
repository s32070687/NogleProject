package com.eulsapet.nogleproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eulsapet.nogleproject.repository.ApiService.Companion.WebSocketInstance
import com.eulsapet.nogleproject.repository.BaseCallBackStatus
import com.eulsapet.nogleproject.repository.FragmentARepository
import com.eulsapet.nogleproject.repository.model.MarketList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString

class FragmentAViewModel(
    private val mainRepository: FragmentARepository
): ViewModel() {

    private val _data = MutableStateFlow<BaseCallBackStatus<MarketList>>(BaseCallBackStatus.LOADING(true))

    val data: StateFlow<BaseCallBackStatus<MarketList>> = _data

    fun getMarketList() {
        _data.value = BaseCallBackStatus.LOADING(true)
        viewModelScope.launch {
            mainRepository.getMarkList().collectLatest {
                _data.value = it
            }
        }

        viewModelScope.launch {
            mainRepository.connectWebSocket()
        }
    }
}