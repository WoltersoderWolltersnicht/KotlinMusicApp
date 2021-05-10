package com.example.kotlinmusicapp.data.network.apis

import com.example.kotlinmusicapp.data.responses.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterApi {

    @FormUrlEncoded
    @POST("register.php")
    suspend fun register(
        @Field("name")name:String,
        @Field("email") email:String,
        @Field("password")password: String
        //Returns a register Response class with te user
    ) : RegisterResponse

}