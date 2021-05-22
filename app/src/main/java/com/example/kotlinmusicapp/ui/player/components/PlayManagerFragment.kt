package com.example.kotlinmusicapp.ui.player.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlinmusicapp.data.repository.PlayerManagerRepository
import com.example.kotlinmusicapp.databinding.FragmentPlayManagerBinding
import com.example.kotlinmusicapp.databinding.FragmentSongPlayerBinding
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.player.SongPlayerViewModel

class PlayManagerFragment : BaseFragment<PlayerViewModel, FragmentPlayManagerBinding, PlayerManagerRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    //Returns Actual VM Class
    override fun getViewModel() = PlayerViewModel::class.java

    //Returns Actual FragmentBinding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) : FragmentPlayManagerBinding = FragmentPlayManagerBinding.inflate(inflater, container, false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = PlayerManagerRepository()

}