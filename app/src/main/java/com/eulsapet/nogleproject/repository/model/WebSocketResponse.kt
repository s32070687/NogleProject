package com.eulsapet.nogleproject.repository.model

data class WebSocket(
    val topic: String = "",
    val id: Int = 0,
    val time: Long = 0,
    val data: List<Data> = emptyList()
)



