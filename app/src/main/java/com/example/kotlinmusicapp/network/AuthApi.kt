package com.example.kotlinmusicapp.network

import com.example.kotlinmusicapp.responses.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi{

    @FormUrlEncoded
    @POST("users.php")
    suspend fun login(
        @Field("email") email:String,
        @Field("password")password: String
    ) : LoginResponse

}