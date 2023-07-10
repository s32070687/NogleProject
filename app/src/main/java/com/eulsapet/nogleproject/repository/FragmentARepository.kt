package com.eulsapet.nogleproject.repository

import android.util.Log
import com.eulsapet.nogleproject.repository.model.MarketList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class FragmentARepository(
    private val apiService: ApiService = ApiService.Instance,
    private val socketService: Request = ApiService.WebSocketInstance,
    private val client: OkHttpClient = ApiService.okHttpClient
) {
    /**
     * 市場列表
     */
    suspend fun getMarkList(): Flow<BaseCallBackStatus<MarketList>> = flow {
        apiService.runCatching {
            markList()
        }.onSuccess {
            when {
                it.data.isEmpty() -> emit(BaseCallBackStatus.ERROR("empty"))
                else -> emit(BaseCallBackStatus.SUCCESS(it))
            }
        }.onFailure {
            emit(BaseCallBackStatus.ERROR("error"))
        }
    }

    /**
     * WebSocket
     */
    suspend fun connectWebSocket() {
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

        val webSocket = client.newWebSocket(socketService, socketListener)
        webSocket.request()
    }
}