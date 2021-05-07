package com.example.githubuserapi.utils

data class ResourceQuery<out T>(val status: ListLoadStatus, val data: T?, val message: String?){
    companion object{
        fun <T> filled(data: T?): ResourceQuery<T> {
            return ResourceQuery(ListLoadStatus.FILLED, data, null)
        }

        fun <T> empty(data: T?): ResourceQuery<T> {
            return ResourceQuery(ListLoadStatus.EMPTY, data, null)
        }

        fun <T> error(data: T?, msg: String): ResourceQuery<T> {
            return ResourceQuery(ListLoadStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResourceQuery<T> {
            return ResourceQuery(ListLoadStatus.LOADING, data, null)
        }
    }
}
