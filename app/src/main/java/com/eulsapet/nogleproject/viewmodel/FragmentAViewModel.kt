package com.eulsapet.nogleproject.viewmodel

import androidx.lifecycle.*
import com.eulsapet.nogleproject.repository.BaseCallBackStatus
import com.eulsapet.nogleproject.repository.FragmentARepository
import com.eulsapet.nogleproject.repository.model.MarketList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentAViewModel(
    private val mainRepository: FragmentARepository
): ViewModel() {

    private val _data = MutableLiveData<BaseCallBackStatus<MarketList>>(BaseCallBackStatus.LOADING(true))

    val data: LiveData<BaseCallBackStatus<MarketList>> = _data

    val messageData: LiveData<String> get() = mainRepository.msg

    val mediatorLiveData = MediatorLiveData<Any>()

    init {
        mediatorLiveData.addSource(data) {
            mediatorLiveData.value = it
        }

        mediatorLiveData.addSource(messageData) {
            mediatorLiveData.value = it
        }
    }

    fun getMarketList() {
        _data.value = BaseCallBackStatus.LOADING(true)
        viewModelScope.launch {
            launch {
                mainRepository.getMarkList().collectLatest {
                    _data.value = it
                }
            }

            launch {
                mainRepository.connectWebSocket()
            }
        }
    }
}