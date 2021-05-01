package com.example.kotlinmusicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.kotlinmusicapp.data.UserPreferences
import com.example.kotlinmusicapp.ui.auth.AuthActivity
import com.example.kotlinmusicapp.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPreferences = UserPreferences(this)
        userPreferences.accessToken.asLiveData().observe(this, Observer {

            if(it == null) AuthActivity::class.java else HomeActivity::class.java
            startActivity(Intent(this,AuthActivity::class.java))
        })

    }
}