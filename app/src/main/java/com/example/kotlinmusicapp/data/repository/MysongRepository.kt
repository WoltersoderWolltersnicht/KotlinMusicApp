package com.example.kotlinmusicapp.data.repository;

import com.example.kotlinmusicapp.data.network.apis.MySongsApi
import com.example.kotlinmusicapp.data.network.apis.SongsApi;

class MysongRepository(
    private val api: MySongsApi,
) : BaseRepository () {

    suspend fun getSongList() = safeApiCall { api.getSongList("","1") }

}
