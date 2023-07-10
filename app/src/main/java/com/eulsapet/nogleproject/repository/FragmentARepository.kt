package com.eulsapet.nogleproject.repository

import kotlinx.coroutines.flow.flow
import okhttp3.Request

class FragmentARepository(
    private val apiService: ApiService = ApiService.Instance,
    private val socketService: Request = ApiService.WebSocketInstance
) {
    /**
     * 市場列表
     */
    suspend fun getMarketList() = apiService.markList()

    /**
     * WebSocket
     */
    suspend fun connectWebSocket() = flow {

    }
}