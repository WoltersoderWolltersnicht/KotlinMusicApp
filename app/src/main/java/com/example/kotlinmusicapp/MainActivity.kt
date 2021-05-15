package com.example.kotlinmusicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var  navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        navController = Navigation.findNavController(this, R.id.fragmentMain)

        NavigationUI.setupWithNavController(findViewById(R.id.navigationView) as NavigationView,navController)
        NavigationUI.setupActionBarWithNavController(this, navController, findViewById(R.id.drawerLayout))

    }

    public fun changeFragment(action : NavDirections){

        findNavController(this,R.id.fragmentMain).navigate(action)

    }

}