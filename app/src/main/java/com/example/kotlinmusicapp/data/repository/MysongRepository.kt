package com.example.kotlinmusicapp.data.repository;

import com.example.kotlinmusicapp.data.network.apis.SongsApi;

class MysongRepository (
    private val api : SongsApi,
) : BaseRepository () {

}
