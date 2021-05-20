package com.example.kotlinmusicapp.data.network.apis

import com.example.kotlinmusicapp.data.responses.SongListResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SongsApi {
    @FormUrlEncoded
    @POST("songs.php")
    suspend fun getSongList(
        @Field("name") name:String,
    //Returns a Song List Response class with te user
    ) : SongListResponse
}