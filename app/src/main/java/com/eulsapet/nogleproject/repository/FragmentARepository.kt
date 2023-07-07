package com.eulsapet.nogleproject.repository

class FragmentARepository(
    private val apiService: ApiService = ApiService.Instance
) {
    suspend fun getMarketList() = apiService.markList()
}