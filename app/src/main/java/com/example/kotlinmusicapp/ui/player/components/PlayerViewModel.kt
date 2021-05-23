package com.example.kotlinmusicapp.ui.player.components

import android.media.MediaPlayer
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.kotlinmusicapp.data.repository.PlayerManagerRepository
import java.io.IOException

class PlayerViewModel (

    private val repository: PlayerManagerRepository

        ) : ViewModel() {

            var mediaPlayer = MediaPlayer()

    public fun play(url: String, context: FragmentActivity?){

        try {
            mediaPlayer.setDataSource("http://lluc.rottinghex.com/sound.mp3")
        } catch (e: IOException) {
            Log.e("", e.message+"")
        }
        mediaPlayer.prepareAsync();

        mediaPlayer.setOnPreparedListener { mp -> mp.start() }
    }




    private fun millisecondsToTimer(milliSeconds:Long):String{

        val timerString:String = ""
        val secondsStrings : String

        //Mucho palo escribir tanto
        val hours:Int = (milliSeconds / (1000*60*60)) as Int
        val minutes : Int = ((milliSeconds%(1000*60*60)) as Int) / (1000*60)

        return ""
    }
}