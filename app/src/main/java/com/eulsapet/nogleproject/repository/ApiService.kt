package com.eulsapet.nogleproject.repository

import com.eulsapet.nogleproject.repository.model.MarketList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface ApiService {

    companion object {
        private const val BASE_URL = "https://api.btse.com/"
        private fun createApiClient(): ApiService {
            val builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return builder.create()
        }
        val Instance by lazy { createApiClient() }
    }

    @GET("futures/api/inquire/initial/market")
    suspend fun markList(): MarketList
}