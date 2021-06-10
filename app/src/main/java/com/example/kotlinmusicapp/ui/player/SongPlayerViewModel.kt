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
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.data.responses.RegisterResponse
import kotlinx.coroutines.launch

class SongPlayerViewModel (
    private val repository: SongPlayerRepository
) : ViewModel() {

    private val TAG = "MainActivityViewModel"

    private val _favRes: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData<Resource<RegisterResponse>>()
    val favRes: LiveData<Resource<RegisterResponse>> get() = _favRes

    private val _mBinder: MutableLiveData<PlayerService.MyBinder?> = MutableLiveData<PlayerService.MyBinder?>()
    val mBinder: LiveData<PlayerService.MyBinder?> get() = _mBinder

    val musicManager = MusicManager();

    fun setFav(){
        val song = musicManager.getActualSong()
        if(song.fav==1){
            song.fav = 0
            viewModelScope.launch {
                _favRes.value = repository.removeFav(song.sgn_id.toString())
            }
        }else {
            song.fav = 1
            viewModelScope.launch {
                _favRes.value = repository.setFav(song.sgn_id.toString())

            }
        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d(TAG, "ServiceConnection: connected to service.")
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder: PlayerService.MyBinder = iBinder as PlayerService.MyBinder
            _mBinder.value = binder
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(TAG, "ServiceConnection: disconnected from service.")
            _mBinder.value = null
        }
    }

}