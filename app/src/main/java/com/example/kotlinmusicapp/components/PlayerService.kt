package com.example.kotlinmusicapp.components

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private var mp : MediaPlayer? = null
    private val mBinder: IBinder = MyBinder()

    private val _next: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val next: LiveData<Boolean> get() = _next

    private val _currentTime: MutableLiveData<Int> = MutableLiveData<Int>()
    val currentTime: LiveData<Int> get() = _currentTime

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer()
        mp?.setOnErrorListener(this)
        mp?.setOnCompletionListener(this)
    }

    fun start(url: String){
        mp?.setDataSource(url)
        mp?.apply {
            setOnPreparedListener(this@PlayerService)
            prepareAsync() // prepare async to not block main thread
        }
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
        mp.start()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {

        return true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        _next.value=true
        _next.value=false
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
