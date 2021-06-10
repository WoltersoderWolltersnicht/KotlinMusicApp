package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.apis.FavSongsApi
import com.example.kotlinmusicapp.data.network.apis.SongsApi
import com.example.kotlinmusicapp.ui.Utils

class FavsongRepository (
    private val api : FavSongsApi,
) : BaseRepository () {

    suspend fun getSongList() = safeApiCall { api.getSongList("",Utils.userId) }

}