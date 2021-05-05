package com.example.kotlinmusicapp.data.network

import com.example.kotlinmusicapp.data.responses.SongListResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SongsApi {
    @FormUrlEncoded
    @POST("songlist.php")
    suspend fun login(

        //Returns a Song List Response class with te user
    ) : SongListResponse
}