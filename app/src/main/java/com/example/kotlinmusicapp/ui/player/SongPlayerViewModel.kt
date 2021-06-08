package com.example.kotlinmusicapp.ui.player

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.components.MusicManager
import com.example.kotlinmusicapp.components.PlayerService
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.RegisterRepository
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.data.responses.LoginResponse
import com.example.kotlinmusicapp.data.responses.RegisterResponse
import com.example.kotlinmusicapp.data.responses.types.Song
import kotlinx.coroutines.launch

class SongPlayerViewModel (
    private val repository: SongPlayerRepository
) : ViewModel() {

    private val TAG = "MainActivityViewModel"
    public var mService: PlayerService? = null

    private val _favRes: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData<Resource<RegisterResponse>>()
    val favRes: LiveData<Resource<RegisterResponse>> get() = _favRes

    val musicManager = MusicManager();

    fun setFav(){
        val song = musicManager.getActualSong()
        if(song.fav){
            song.fav = false
            viewModelScope.launch {
                _favRes.value = repository.removeFav(song.sgn_id.toString())
            }
        }else {
            song.fav = true
            viewModelScope.launch {
                _favRes.value = repository.setFav(song.sgn_id.toString())

            }
        }
    }

}