package com.example.kotlinmusicapp.components

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinmusicapp.data.responses.types.Song
import com.example.kotlinmusicapp.ui.Utils

class MusicManager() {

    private val TAG = "MusicManager"
    public var mService: PlayerService? = null
    lateinit var songList : List<Song>

    private val _mBinder: MutableLiveData<PlayerService.MyBinder?> = MutableLiveData<PlayerService.MyBinder?>()
    val mBinder: LiveData<PlayerService.MyBinder?> get() = _mBinder

    private val _position: MutableLiveData<Int> = MutableLiveData<Int>()
    val position: LiveData<Int> get() = _position

    private val _isPlaying: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    fun play(){
        if(isPlaying.value==null){
            mService?.start(buidMusicUrl(getActualSong().sgn_url))
            _isPlaying.value =true
        }
        if (isPlaying.value==false){
            mService?.play()
            _isPlaying.value = true
        }
    }

    fun pause(){
        if (isPlaying.value == false){
            mService?.pause()
            _isPlaying.value = false
        }
    }

    fun next(){

        if(position.value!! <songList.size) {
            _position.value = _position.value?.plus(1)
            mService?.change(buidMusicUrl(getActualSong().sgn_url))
            _isPlaying.value = true
        }

    }

    fun previous(){

        if(position.value!!>0) {
            _position.value =_position.value?.minus(1)
            mService?.change(buidMusicUrl(getActualSong().sgn_url))
            _isPlaying.value = true
        }

    }

    fun setPosition(position : Int){
        _position.value=position
    }

    private fun buidMusicUrl(url:String) = Utils.baseUrl+"Music/"+url
    fun getActualSong() = songList[position.value!!]

    //SERVICE
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