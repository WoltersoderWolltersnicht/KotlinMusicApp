package com.example.kotlinmusicapp.ui.player

import androidx.lifecycle.ViewModel
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.data.responses.types.Song

class SongPlayerViewModel (
    private val repository: SongPlayerRepository
) : ViewModel() {

    var songs : List<Song> = emptyList()
    var position : Int = 0
    

}