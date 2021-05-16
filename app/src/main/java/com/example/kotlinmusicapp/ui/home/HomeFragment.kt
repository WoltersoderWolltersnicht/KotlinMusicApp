package com.example.kotlinmusicapp.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.example.kotlinmusicapp.data.repository.HomeRepository
import com.example.kotlinmusicapp.databinding.FragmentHomeBinding
import com.example.kotlinmusicapp.ui.base.BaseFragment


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepository>() {

    private lateinit var  navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar2)

        navController = Navigation.findNavController(binding.fragmentHome)

        var appBarConfiguration = AppBarConfiguration.Builder(navController.graph.id)
            .setDrawerLayout(binding.drawerLayout)
            .build()

        NavigationUI.setupWithNavController(binding.navigationView, navController)
        NavigationUI.setupWithNavController(binding.toolbar2, navController, appBarConfiguration as AppBarConfiguration)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item);
    }

    //Returns Actual VM Class
    override fun getViewModel() = HomeViewModel::class.java

    //Returns Actual Fragment Binding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)

    override fun getFragmentRepository() = HomeRepository()


}