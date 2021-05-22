package com.example.kotlinmusicapp.ui.player.components

import androidx.lifecycle.ViewModel
import com.example.kotlinmusicapp.data.repository.BaseRepository
import com.example.kotlinmusicapp.data.repository.PlayerManagerRepository
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository

class PlayerViewModel (

    private val repository: PlayerManagerRepository

        ) : ViewModel() {

}