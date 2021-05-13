package com.example.kotlinmusicapp.data.responses

import com.example.kotlinmusicapp.data.responses.Type.Song
import java.util.*

data class SongListResponse (
    val respone:StatusResponse,
    val list : LinkedList<Song>
)