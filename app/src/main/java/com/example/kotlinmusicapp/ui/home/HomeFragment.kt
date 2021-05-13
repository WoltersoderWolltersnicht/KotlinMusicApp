package com.example.kotlinmusicapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlinmusicapp.data.repository.HomeRepository
import com.example.kotlinmusicapp.databinding.FragmentHomeBinding

import com.example.kotlinmusicapp.ui.base.BaseFragment


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


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