package com.example.kotlinmusicapp.data.network.apis

import com.example.kotlinmusicapp.data.responses.RegisterResponse
import com.example.kotlinmusicapp.data.responses.SongListResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AddFavApi {
    @FormUrlEncoded
    @POST("addfav.php")
    suspend fun setFav(
        @Field("sgn_id") sng_id:String,
        @Field("usr_id") usr_id:String
    ) : RegisterResponse
}