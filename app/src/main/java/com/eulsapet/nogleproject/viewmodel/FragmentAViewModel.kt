package com.eulsapet.nogleproject.viewmodel

import androidx.lifecycle.*
import com.eulsapet.nogleproject.repository.BaseCallBackStatus
import com.eulsapet.nogleproject.repository.FragmentARepository
import com.eulsapet.nogleproject.repository.model.MarketList
import com.eulsapet.nogleproject.repository.model.WebSocketResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentAViewModel(
    private val mainRepository: FragmentARepository
): ViewModel() {

    // 來自 Restful API
    private val marketListData = MutableLiveData<BaseCallBackStatus<MarketList>>()

    // 來自 WebSock
    private val webSocketMsg = MutableLiveData<WebSocketResponse>()

    // 匯整 以上兩隻資料流
    private val mediatorLiveData = MediatorLiveData<BaseCallBackStatus<MarketList>>(BaseCallBackStatus.LOADING(true))

    // 出口 (給 UI 用)
    val data: LiveData<BaseCallBackStatus<MarketList>> = mediatorLiveData

    init {
        with(mediatorLiveData) {
            addSource(marketListData) {
                postValue(it)
            }
            addSource(webSocketMsg) { webSocketResponse ->
                val marketList = (value as? BaseCallBackStatus.SUCCESS)?.data ?: return@addSource
                val marketListData = marketList.data
                val webSocketResponseData = webSocketResponse.data
                    ?.filter { it.value.type == 1 }
                    ?.map { (_, dataItem) -> dataItem }
                    ?.groupBy { dataItem -> dataItem.name }
                val newMarketList = marketList.copy(
                    data = marketListData?.map {
                        val price = webSocketResponseData?.get(it.symbol)?.first()?.price ?: it.price
                        it.copy(price = price)
                    }
                )
                postValue(BaseCallBackStatus.SUCCESS(newMarketList))
            }
        }
    }

    fun getMarketList() {
        marketListData.value = BaseCallBackStatus.LOADING(true)
        viewModelScope.launch {
            launch {
                mainRepository.getMarkList().collectLatest {
                    marketListData.value = it
                }
            }

            launch {
                mainRepository.connectWebSocket().collectLatest {
                    webSocketMsg.value = it
                }
            }
        }
    }
}