package com.example.kotlinmusicapp

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import com.example.kotlinmusicapp.ui.auth.AuthFragmentDirections

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun changeFragment(action : NavDirections){

        findNavController(this,R.id.fragmentMain).navigate(action)

    }

}