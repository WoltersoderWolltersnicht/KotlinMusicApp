package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.apis.FavSongsApi
import com.example.kotlinmusicapp.data.network.apis.SongsApi

class FavsongRepository (
    private val api : FavSongsApi,
) : BaseRepository () {

    suspend fun getSongList() = safeApiCall { api.getSongList("",1) }

}