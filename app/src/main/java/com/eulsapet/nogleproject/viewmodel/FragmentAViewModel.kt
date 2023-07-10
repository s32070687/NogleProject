package com.eulsapet.nogleproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.eulsapet.nogleproject.repository.ApiService.Companion.WebSocketInstance
import com.eulsapet.nogleproject.repository.FragmentARepository
import com.eulsapet.nogleproject.repository.model.MarketList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString

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