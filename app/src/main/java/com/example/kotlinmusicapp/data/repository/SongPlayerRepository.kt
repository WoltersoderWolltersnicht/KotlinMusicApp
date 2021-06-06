package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.apis.AddFavApi
import com.example.kotlinmusicapp.ui.Utils
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SongPlayerRepository (
    private val api :AddFavApi
        ) : BaseRepository(){

        suspend fun setFav(id:String) = safeApiCall { api.setFav(id, Utils.userId) }

}