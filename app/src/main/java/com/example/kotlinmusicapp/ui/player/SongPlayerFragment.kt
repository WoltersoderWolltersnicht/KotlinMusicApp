package com.example.kotlinmusicapp.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.components.PlayerService
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.AddFavApi
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.data.responses.types.Song
import com.example.kotlinmusicapp.databinding.FragmentSongPlayerBinding
import com.example.kotlinmusicapp.ui.*
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.squareup.picasso.Picasso

class SongPlayerFragment : BaseFragment<SongPlayerViewModel, FragmentSongPlayerBinding, SongPlayerRepository>() {

    private val TAG = "SongPlayerFragment"
    val args : SongPlayerFragmentArgs by navArgs()
    var binded = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.musicManager.setPosition(args.position)
        viewModel.musicManager.songList = args.songs.toList()

        binding.playerSeekBar.max = 100

        binding.play.setOnClickListener {
            if(viewModel.musicManager.isPlaying.value == true){
                viewModel.musicManager.pause()
            }else {
                viewModel.musicManager.play()
            }
        }

        binding.next.setOnClickListener {
            viewModel.musicManager.next()
        }

        binding.previous.setOnClickListener {
            viewModel.musicManager.previous()
        }

        binding.fav.setOnClickListener {
            viewModel.setFav()
        }

        setObservers()

    }

    private fun setObservers(){

        viewModel.musicManager.mService.observe(viewLifecycleOwner,{
            if(it!=null) {
                    viewModel.musicManager.play()
                    update()
            }
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

        viewModel.musicManager.position.observe(viewLifecycleOwner,{
                update()
        })

        viewModel.musicManager.mService?.value?.ready?.observe(viewLifecycleOwner,{
            //update()
        })

        viewModel.musicManager.mService?.value?.currentTime?.observe(viewLifecycleOwner,{
            //Update Time
            binding.playerSeekBar
        })
        viewModel.musicManager.isPlaying.observe(viewLifecycleOwner,{
            //Uptade play buttons
            if(it){
                binding.play.setImageResource(R.drawable.ic_pause)
            }else{
                binding.play.setImageResource(R.drawable.ic_play)
            }
        })

        viewModel.musicManager.mService?.value?.next?.observe(viewLifecycleOwner,{

            viewModel.musicManager.next()

        })

        viewModel.musicManager.mBinder.observe(viewLifecycleOwner,{
            if(viewModel.musicManager.mBinder == null){
                Log.d(TAG, "onChanged: unbound from service")
            }
            else{
                Log.d(TAG, "onChanged: bound to service.")
                viewModel.musicManager.mBinder.value?.getService()?.let { it1 ->
                    viewModel.musicManager.setServer(
                        it1
                    )
                }
            }
        })
    }

    private fun update() {
        val song : Song = viewModel.musicManager.getActualSong()

        binding.name.text = song.sgn_name
        binding.artist.text = song.sgn_artist
        binding.fav.setImageResource(if(song.fav) R.drawable.ic_fav else R.drawable.ic_no_fav)
        Picasso.with(activity).load("http://spotify.rottinghex.com/Img/"+song.sgn_img)
            .placeholder(R.drawable.background)
            .into(binding.img)
        //binding.playerSeekBar
        binding.txtCurrentTime.text = "00:00"
        binding.txtTotalDuration.text = viewModel.musicManager.getActualSongTime().toString()

    }

    //TODO Set More Observers to Observer Data from MediaPlayer

    override fun onStart() {
        super.onStart()
        startService()
    }

    override fun onStop() {
        super.onStop()
        if(binded) {
            activity?.unbindService(viewModel.musicManager.getServiceConnection())
            binded = false
        }
    }

    fun startService(){
        if (viewModel.musicManager.mService.value==null) {
            val miReproductor = Intent(activity, PlayerService::class.java)
            activity?.startService(miReproductor)
            binded=true
            bindService()
        }else{
            Log.e("Prueba",args.position.toString())
        }
    }

    fun bindService(){
        val miReproductor = Intent(activity, PlayerService::class.java)
        activity?.bindService(miReproductor,viewModel.musicManager.getServiceConnection(), Context.BIND_AUTO_CREATE)
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