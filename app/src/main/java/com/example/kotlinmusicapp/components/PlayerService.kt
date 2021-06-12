package com.example.kotlinmusicapp.components

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.os.postAtTime
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

        var hilo : Thread = Thread {
            while (true) {
                if (mp?.isPlaying == true) {
                    Thread.sleep(1000)
                    _currentTime.postValue(mp?.currentPosition!!)
                }
            }
        }

        hilo.start()

    }

    fun start(url: String){
        if(!isStart){
            mp?.reset()
        }
            mp?.setDataSource(url)
            mp?.apply {
                setOnPreparedListener(this@PlayerService)
                setOnInfoListener(this@PlayerService)
                setOnCompletionListener(this@PlayerService)
                setOnErrorListener(this@PlayerService)
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

    fun setTime(milliseconds:Int){
        mp?.seekTo(milliseconds)
    }

    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
        _ready.value = ready.value != true
        Log.e("val",_ready.value.toString())

    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.e("MediaPlayerError","Error")
        return true
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        Log.i("MediaPlayerCompetion","Next Song")
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
        mp = null
        stopSelf()
    }

}
