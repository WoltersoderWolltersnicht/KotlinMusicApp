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

    private val _next: MutableLiveData<Int> = MutableLiveData<Int>()
    val next: LiveData<Int> get() = _next

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer()
        mp?.setOnErrorListener(this)
    }

    fun change(url : String){
        mp?.reset()
        play(url)
    }

    fun play(url:String){
        if (isPaused()){
            mp?.start()
        }else {
            mp?.setDataSource(url)
            mp?.apply {
                setOnPreparedListener(this@PlayerService)
                prepareAsync() // prepare async to not block main thread
            }
        }
    }

    fun pause(){
        if(mp?.isPlaying==true) mp?.pause()
    }

    private fun isPaused() = !mp?.isPlaying!! && mp?.currentPosition!! > 1

    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {

        return true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        _next.value=1
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
