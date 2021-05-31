package com.example.kotlinmusicapp.ui.player

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinmusicapp.components.PlayerService
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.data.responses.types.Song

class SongPlayerViewModel (
    private val repository: SongPlayerRepository
) : ViewModel() {

    private val TAG = "MainActivityViewModel"
    public var mService: PlayerService? = null

    var songs : List<Song> = emptyList()
    var position : Int = 0

    private val _mBinder: MutableLiveData<PlayerService.MyBinder?> = MutableLiveData<PlayerService.MyBinder?>()
    val mBinder: LiveData<PlayerService.MyBinder?> get() = _mBinder

    val _isPlaying: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    fun play(){
        load()
        _isPlaying.value=true
    }

    fun pause(){
        mService?.pause()
        _isPlaying.value=false
    }

    fun next(){
        if (position<songs.size-1) position++
        mService?.change("http://spotify.rottinghex.com/Music/"+songs[position].sgn_url)
        _isPlaying.value = true

    }

    fun previous(){
        if (position>0) position--
        mService?.change("http://spotify.rottinghex.com/Music/"+songs[position].sgn_url)
        _isPlaying.value = true
    }

    private fun load(){
        mService?.play("http://spotify.rottinghex.com/Music/"+songs[position].sgn_url)
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection
    {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d(TAG, "ServiceConnection: connected to service.")
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder: PlayerService.MyBinder = iBinder as PlayerService.MyBinder
            _mBinder.postValue(binder)
            mService = binder.getService()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(TAG, "ServiceConnection: disconnected from service.")
            _mBinder.postValue(null)
            mService = null
        }
    }

    fun getServiceConnection() = serviceConnection

}