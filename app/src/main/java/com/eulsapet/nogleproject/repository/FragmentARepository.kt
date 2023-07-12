package com.eulsapet.nogleproject.repository

import android.util.Log
import com.eulsapet.nogleproject.repository.model.MarketList
import com.eulsapet.nogleproject.repository.model.WebSocketResponse
import com.eulsapet.nogleproject.repository.model.WebSocketResponseTypeToken
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
    companion object {
        private const val WEBSOCKET_REQUEST = "{\"op\": \"subscribe\", \"args\": [\"coinIndex\"]}"
    }

    /**
     * 市場列表
     */
    fun getMarkList(): Flow<BaseCallBackStatus<MarketList>> = flow {
        apiService.runCatching {
            markList()
        }.onSuccess {
            when {
                it.data.isNullOrEmpty() -> emit(BaseCallBackStatus.ERROR("empty"))
                else -> emit(BaseCallBackStatus.SUCCESS(it))
            }
        }.onFailure {
            emit(BaseCallBackStatus.ERROR("error"))
        }
    }

    /**
     * WebSocket
     */
    fun connectWebSocket(): Flow<WebSocketResponse> = callbackFlow {
        val socketListener = object : WebSocketListener() {

            val gson = Gson()

            override fun onOpen(webSocket: WebSocket, response: Response) {
                webSocket.send(WEBSOCKET_REQUEST)
                Log.e("Jason", "onOpen response: $response")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val data: WebSocketResponse = gson.fromJson(text, WebSocketResponseTypeToken.type)
                if (data.topic != "coinIndex") return
                trySend(data)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.e("Jason", "onMessage bytes: $bytes")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("Jason", "onFailure Throwable: ${t.message}")
                webSocket.cancel()
            }
        }

        val webSocket = client.newWebSocket(socketService, socketListener)
        webSocket.request()

        awaitClose { webSocket.cancel() }
    }
}