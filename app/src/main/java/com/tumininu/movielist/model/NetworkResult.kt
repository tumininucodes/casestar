package com.tumininu.movielist.model

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val error: Throwable) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}