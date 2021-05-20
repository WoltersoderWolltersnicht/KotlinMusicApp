package com.example.kotlinmusicapp.data.repository;

import com.example.kotlinmusicapp.data.network.apis.SongsApi;

class SongsRepository (
    private val api : SongsApi,
) : BaseRepository () {

    suspend fun getSongList() = safeApiCall { api.getSongList("") }

}
