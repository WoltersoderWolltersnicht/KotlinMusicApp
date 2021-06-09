package com.example.kotlinmusicapp.components

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener {

    var mp : MediaPlayer? = MediaPlayer()
    private val mBinder: IBinder = MyBinder()
    var isStart = true

    private val _next: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val next: LiveData<Boolean> get() = _next

    private val _ready: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val ready: LiveData<Boolean> get() = _ready

    private val _currentTime: MutableLiveData<Int> = MutableLiveData<Int>()
    val currentTime: LiveData<Int> get() = _currentTime

    override fun onCreate() {
        super.onCreate()
        mp?.setOnErrorListener(this)
        mp?.setOnCompletionListener(this)
    }

    fun start(url: String){
        if(!isStart){
            mp?.reset()
        }
            mp?.setDataSource(url)
            mp?.apply {
                setOnPreparedListener(this@PlayerService)
                prepareAsync() // prepare async to not block main thread
            }
            isStart=false

    }

    fun play(){
        mp?.start()
    }

    fun pause(){
        mp?.pause()
    }

    fun change(url : String){
        mp?.reset()
        start(url)
    }

    override fun onPrepared(mp: MediaPlayer) {
        if(ready.value == true) _ready.value = false else _ready.value = true
        mp.start()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        _next.value = next.value != true
        return true
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        _ready.value = ready.value != true
        return true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        _next.value = next.value != true
    }

    //Binder
    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    inner class MyBinder : Binder() {
        fun getService() : PlayerService? {
            return this@PlayerService
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

}
