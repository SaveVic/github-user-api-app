package com.example.githubuserapi.utils

data class ResourceUser<out T>(val status: NetworkStatus, val data: T?, val message: String?){
    companion object{
        fun <T> success(data: T?): ResourceUser<T> {
            return ResourceUser(NetworkStatus.SUCCESS, data, null)
        }

        fun <T> error(data: T?, msg: String): ResourceUser<T> {
            return ResourceUser(NetworkStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResourceUser<T> {
            return ResourceUser(NetworkStatus.LOADING, data, null)
        }
    }
}
