package com.eulsapet.nogleproject.repository

import com.eulsapet.nogleproject.repository.model.MarketList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Request

class FragmentARepository(
    private val apiService: ApiService = ApiService.Instance,
    private val socketService: Request = ApiService.WebSocketInstance
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
//    suspend fun connectWebSocket() = flow {
//
//    }
}