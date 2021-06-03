package com.example.kotlinmusicapp.data.network.apis

import com.example.kotlinmusicapp.data.responses.SongListResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FavSongsApi {
    @FormUrlEncoded
    @POST("favsongs.php")
    suspend fun getSongList(
        @Field("name") name:String,
        @Field("id") id:Int,
        //Returns a Song List Response class with te user
    ) : SongListResponse
}