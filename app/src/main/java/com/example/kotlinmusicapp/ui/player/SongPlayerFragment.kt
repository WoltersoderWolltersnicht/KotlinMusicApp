package com.example.kotlinmusicapp.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.databinding.FragmentSongPlayerBinding
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.player.components.PlayManagerFragment

class SongPlayerFragment : BaseFragment<SongPlayerViewModel, FragmentSongPlayerBinding, SongPlayerRepository>() {

    val args : SongPlayerFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.position = args.position
        viewModel.songs = args.songs.toList()

        /*
        Log.v("Position", viewModel.position.toString())
        viewModel.songs.forEach{
            Log.v("Song:", it.name)
        }
        */
        val transaction = childFragmentManager.beginTransaction()

        transaction.replace(binding.fragmentPlayer.id, PlayManagerFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //Returns Actual VM Class
    override fun getViewModel() = SongPlayerViewModel::class.java

    //Returns Actual FragmentBinding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) : FragmentSongPlayerBinding = FragmentSongPlayerBinding.inflate(inflater, container, false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = SongPlayerRepository()

}