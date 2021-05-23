package com.example.kotlinmusicapp.ui.player.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlinmusicapp.data.repository.PlayerManagerRepository
import com.example.kotlinmusicapp.databinding.FragmentPlayManagerBinding
import com.example.kotlinmusicapp.ui.base.BaseFragment

class PlayManagerFragment : BaseFragment<PlayerViewModel, FragmentPlayManagerBinding, PlayerManagerRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.playerSeekBar.max = 100

        binding.play.setOnClickListener {

            viewModel.play("http://10.0.2.2/projects/userapp/Music/Born_For_Burning.mp3",activity);

        }

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