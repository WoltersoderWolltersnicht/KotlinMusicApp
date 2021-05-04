package com.example.kotlinmusicapp.data.network

import com.example.kotlinmusicapp.data.responses.LoginResponse
import retrofit2.http.GET

interface UserApi {

    @GET("user")
    suspend fun getUser(): LoginResponse

}