package com.eulsapet.nogleproject.repository

import com.eulsapet.nogleproject.repository.model.MarketList
import com.eulsapet.nogleproject.repository.model.WebSocket
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface ApiService {

    companion object {
        private const val BASE_URL = "https://api.btse.com/"
        private const val WEB_SOCKET_BASE_URL = "wss://ws.btse.com/ws/futures"

        private fun createApiClient(): ApiService {
            val builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return builder.create()
        }
        val Instance by lazy { createApiClient() }

        private fun createWebSocketClient(): Request {
            val builder = Request.Builder()
                .url(WEB_SOCKET_BASE_URL)

            return builder.build()
        }
        val WebSocketInstance by lazy { createWebSocketClient() }
    }

    @GET("futures/api/inquire/initial/market")
    suspend fun markList(): MarketList

}