package com.example.kotlinmusicapp.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlinmusicapp.data.network.apis.SongsApi
import com.example.kotlinmusicapp.data.repository.MysongRepository
import com.example.kotlinmusicapp.databinding.FragmentMysongBinding
import com.example.kotlinmusicapp.ui.base.BaseFragment

class MysongFragment : BaseFragment<MysongViewModel, FragmentMysongBinding, MysongRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.root

    }

    override fun getViewModel() = MysongViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) : FragmentMysongBinding = FragmentMysongBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()  = MysongRepository(remoteDataSource.buildApi(SongsApi::class.java))

}