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
    }

    fun connectSocket() {
        viewModelScope.launch {
            val client = OkHttpClient()
            val request = WebSocketInstance

            val socketListener = object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    val subscribeRequest = """{"op": "subscribe", "args": ["coinIndex"]}"""
                    webSocket.send(subscribeRequest)
                    Log.e("Jason", "onOpen response: $response")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    Log.e("Jason", "onMessage text: $text")
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    Log.e("Jason", "onMessage bytes: $bytes")
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    Log.e("Jason", "onFailure Throwable: ${t.message}")
                }
            }

            val webSocket = client.newWebSocket(request, socketListener)
            webSocket.request()
        }
    }
}