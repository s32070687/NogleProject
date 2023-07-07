package com.eulsapet.nogleproject.repository

class MainRepository(
    private val apiService: ApiService = ApiService.Instance
) {
    suspend fun getMarketList() = apiService.markList()
}