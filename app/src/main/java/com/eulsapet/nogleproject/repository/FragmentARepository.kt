package com.eulsapet.nogleproject.repository

class FragmentARepository(
    private val apiService: ApiService = ApiService.Instance,
    private val socketService: ApiService = ApiService.WebSocketInstance
) {
    /**
     * 市場列表
     */
    suspend fun getMarketList() = apiService.markList()

    /**
     * WebSocket
     */
    suspend fun connectWebSocket() = socketService.webSocket()
}