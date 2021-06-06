package com.example.kotlinmusicapp.ui.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.kotlinmusicapp.MainActivity
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.components.PlayerService
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.AddFavApi
import com.example.kotlinmusicapp.data.network.apis.AuthApi
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.data.responses.types.Song
import com.example.kotlinmusicapp.databinding.FragmentSongPlayerBinding
import com.example.kotlinmusicapp.ui.*
import com.example.kotlinmusicapp.ui.auth.AuthFragmentDirections
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.squareup.picasso.Picasso

class SongPlayerFragment : BaseFragment<SongPlayerViewModel, FragmentSongPlayerBinding, SongPlayerRepository>() {

    private val TAG = "SongPlayerFragment"
    val args : SongPlayerFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.position = args.position
        viewModel.songs = args.songs.toList()
        viewModel._isPlaying.value = false

        binding.playerSeekBar.max = 100

        setObservers()

        binding.play.setOnClickListener {
            if (viewModel.isPlaying.value==true) viewModel.pause()
            else viewModel.play()
        }

        binding.next.setOnClickListener {
            viewModel.next()
            update()
        }

        binding.previous.setOnClickListener {
            viewModel.previous()
            update()
        }

        binding.fav.setOnClickListener {
            viewModel.setFav()
        }

        update()
        viewModel.play()
    }

    private fun setObservers(){
        viewModel.mBinder.observe(viewLifecycleOwner,{
            viewModel.mService?.next?.observe(viewLifecycleOwner,{
                viewModel.next()
            })
        })
        viewModel.isPlaying.observe(viewLifecycleOwner,  {
            updatePlayer(it)
        })

        viewModel.favRes.observe(viewLifecycleOwner,{
            when (it) {
                //On Success
                is Resource.Success -> {
                    update()
                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
            }
        })

    }

    private fun updatePlayer(isPlaying : Boolean){
        if (isPlaying){
            binding.play.setImageResource(R.drawable.ic_play)
        }else{
            binding.play.setImageResource(R.drawable.ic_pause)
        }
    }

    private fun update() {
        val song : Song = viewModel.songs[viewModel.position]

        binding.name.text = song.sgn_name
        binding.artist.text = song.sgn_artist
        binding.fav.setImageResource(if(song.fav) R.drawable.ic_fav else R.drawable.ic_no_fav)
        Picasso.with(activity).load("http://spotify.rottinghex.com/Img/"+song.sgn_img)
            .placeholder(R.drawable.background)
            .into(binding.img)
        //binding.playerSeekBar

    }

    //TODO Set More Observers to Observer Data from MediaPlayer

    override fun onResume() {
        super.onResume()
        startService()
    }

    override fun onStop() {
        super.onStop()
        if(viewModel.mBinder.value!=null){
            activity?.unbindService(viewModel.getServiceConnection())
        }
    }

    fun startService(){
        val miReproductor = Intent(activity, PlayerService::class.java)
        //miReproductor.putExtra()
        activity?.startService(miReproductor)

        bindService()
    }

    fun bindService(){
        val miReproductor = Intent(activity, PlayerService::class.java)
        activity?.bindService(miReproductor,viewModel.getServiceConnection(), Context.BIND_AUTO_CREATE)
    }

    //Returns Actual VM Class
    override fun getViewModel() = SongPlayerViewModel::class.java

    //Returns Actual FragmentBinding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) : FragmentSongPlayerBinding = FragmentSongPlayerBinding.inflate(inflater, container, false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = SongPlayerRepository(remoteDataSource.buildApi(AddFavApi::class.java))

}