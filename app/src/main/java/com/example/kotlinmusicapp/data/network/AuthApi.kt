package com.example.kotlinmusicapp.data.network

import com.example.kotlinmusicapp.data.responses.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi{

    @FormUrlEncoded
    @POST("users.php")
    suspend fun login(
        @Field("email") email:String,
        @Field("password")password: String
    //Returns a login Response class with te user
    ) : LoginResponse

}