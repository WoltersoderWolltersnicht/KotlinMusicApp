package com.example.kotlinmusicapp.data.responses

import com.example.kotlinmusicapp.data.responses.types.Song

data class SongListResponse (
    val status:StatusResponse,
    val songs : List<Song>
)