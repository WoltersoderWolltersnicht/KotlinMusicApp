package com.example.kotlinmusicapp.network

import okhttp3.ResponseBody

sealed class Resource <out T>{

    data class Success<out T>(val value: T):Resource<T>()
    data class  Failure(
        val inNetworkError: Boolean,
        val errorCode:Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()

}