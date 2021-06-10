package com.example.kotlinmusicapp.data.repository;

import com.example.kotlinmusicapp.data.network.apis.MySongsApi
import com.example.kotlinmusicapp.data.network.apis.SongsApi;
import com.example.kotlinmusicapp.ui.Utils

class MysongRepository(
    private val api: MySongsApi,
) : BaseRepository () {

    suspend fun getSongList() = safeApiCall { api.getSongList("",Utils.userId) }

}
