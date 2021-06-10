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

    lateinit var songList : List<Song>

    private val _mBinder: MutableLiveData<PlayerService.MyBinder?> = MutableLiveData<PlayerService.MyBinder?>()
    val mBinder: LiveData<PlayerService.MyBinder?> get() = _mBinder

    private val _mService: MutableLiveData<PlayerService?> = MutableLiveData<PlayerService?>()
    val mService: LiveData<PlayerService?> get() = _mService

    private val _position: MutableLiveData<Int> = MutableLiveData<Int>()
    val position: LiveData<Int> get() = _position

    private val _isPlaying: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    fun play(){
        if(isPlaying.value==null){
            _mService.value?.start(buidMusicUrl(getActualSong().sgn_url))
            _isPlaying.value =true
        }
        if (isPlaying.value==false){
            _mService.value?.play()
            _isPlaying.value = true
        }
    }

    fun pause(){
        if (isPlaying.value == true){
            _mService.value?.pause()
            _isPlaying.value = false
        }
    }

    fun next(){

        if(position.value!!+1 <songList.size) {
            _position.value = _position.value?.plus(1)
            _mService.value?.change(buidMusicUrl(getActualSong().sgn_url))
            _isPlaying.value = true
        }

    }

    fun previous(){

        if(position.value!!>0) {
            _position.value =_position.value?.minus(1)
            _mService.value?.change(buidMusicUrl(getActualSong().sgn_url))
            _isPlaying.value = true
        }

    }

    fun setPosition(position : Int){
        _position.value=position
        _mService.value?.change(buidMusicUrl(getActualSong().sgn_url))
    }
    fun setServer(server : PlayerService){
        _mService.value=server
    }

    private fun buidMusicUrl(url:String) = Utils.baseUrl+"Music/"+url
    fun getActualSong() = songList[position.value!!]
    fun getActualSongTime(): Int? {
        try {
            return _mService.value?.mp?.duration
        }catch (e:Exception){

        }
        return 0
    }
    //SERVICE
    private val serviceConnection: ServiceConnection = object : ServiceConnection
    {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d(TAG, "ServiceConnection: connected to service.")
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder: PlayerService.MyBinder = iBinder as PlayerService.MyBinder
            _mBinder.postValue(binder)
            _mService.value = binder.getService()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(TAG, "ServiceConnection: disconnected from service.")
            _mBinder.postValue(null)
            _mService.value = null
        }
    }
    fun getServiceConnection() = serviceConnection

}