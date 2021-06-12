package com.example.kotlinmusicapp.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinmusicapp.MainActivity
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.components.PlayerService
import com.example.kotlinmusicapp.ui.Utils
import com.example.kotlinmusicapp.ui.auth.AuthFragmentDirections
import com.example.kotlinmusicapp.ui.changeFragment
import com.example.kotlinmusicapp.ui.home.HomeFragmentDirections


class LogoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val action = HomeFragmentDirections.actionHomeFragmentToAuthFragment()
        activity?.changeFragment(MainActivity::class.java,action)
        activity?.stopService(Intent(activity, PlayerService::class.java))
        return inflater.inflate(R.layout.fragment_logout, container, false)

    }

}