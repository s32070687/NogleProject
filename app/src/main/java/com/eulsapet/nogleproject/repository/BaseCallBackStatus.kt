package com.eulsapet.nogleproject.repository

sealed interface BaseCallBackStatus<T: Any> {
    class LOADING<T: Any>(val loading: Boolean): BaseCallBackStatus<T>
    data class SUCCESS<T: Any>(val data: T): BaseCallBackStatus<T>
    data class ERROR<T: Any>(val message: String?): BaseCallBackStatus<T>
}