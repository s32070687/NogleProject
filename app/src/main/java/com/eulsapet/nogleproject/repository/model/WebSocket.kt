package com.eulsapet.nogleproject.repository.model

import com.google.gson.reflect.TypeToken

data class WebSocketResponse(
    val topic: String? = null,
    val id: Int? = null,
    val data: Map<String, DataItem>? = null,
)

data class DataItem(
    val id: String? = null,
    val name: String? = null,
    val type: Int? = null,
    val price: Double? = null,
    val gains: Double? = null,
    val open: Double? = null,
    val high: Double? = null,
    val low: Double? = null,
    val volume: Double? = null,
    val amount: Double? = null,
)

object WebSocketResponseTypeToken: TypeToken<WebSocketResponse>()


