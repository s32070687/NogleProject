package com.eulsapet.nogleproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
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
            val call = mainRepository.connectWebSocket()
        }
//        val client = OkHttpClient()
//        val request = Request.Builder().url("wss://ws.btse.com/ws/future").build()
//
//        val socketListener = object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: Response) {
//                val subscribeRequest = """{"op": "subscribe", "args": ["coinIndex"]}"""
//                webSocket.send(subscribeRequest)
//            }
//
//            override fun onMessage(webSocket: WebSocket, text: String) {
//                Log.e("Jason", "Received message: $text")
//            }
//
//            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//                // 处理收到的字节消息
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//                Log.e("Jason", "WebSocket failure: ${t.message}")
//            }
//        }
//
//        val webSocket = client.newWebSocket(request, socketListener)
//        webSocket.awaitClosed()
    }
}