package com.example.kotlinmusicapp.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
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

class SongPlayerFragment : BaseFragment<SongPlayerViewModel, FragmentSongPlayerBinding, SongPlayerRepository>(),
    SeekBar.OnSeekBarChangeListener {

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

        binding.dropdown.setOnClickListener {
            activity?.onBackPressed()
        }


        binding.playerSeekBar.setOnSeekBarChangeListener(this)

    }

    private fun setObservers(){

        viewModel.musicManager.mService.observe(viewLifecycleOwner,{
            if(it!=null) {
                    viewModel.musicManager.play()
            }
        })

        viewModel.favRes.observe(viewLifecycleOwner,{
            when (it) {
                //On Success
                is Resource.Success -> {
                    binding.fav.animation = AnimationUtils.loadAnimation(context,R.anim.fav_anim)
                    update()
                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
            }
        })

        viewModel.musicManager.isPlaying.observe(viewLifecycleOwner,{
            //Uptade play buttons
            if(it){
                binding.play.setImageResource(R.drawable.ic_pause)
            }else{
                binding.play.setImageResource(R.drawable.ic_play)
            }
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

        viewModel.musicManager.mService.observe(viewLifecycleOwner,{
            if(it!=null){
                it.ready.observe(viewLifecycleOwner,{
                    update()
                })

                it.currentTime.observe(viewLifecycleOwner,{
                        updateCurrent(it)
                })

                it.next.observe(viewLifecycleOwner,{
                    viewModel.musicManager.next()
                })
            }
        })

    }

    private fun setTime(time : Int){
        viewModel.musicManager.mService.value?.setTime(time)
    }

    private fun update() {
        val song : Song = viewModel.musicManager.getActualSong()

        binding.name.text = song.sgn_name
        binding.artist.text = song.sgn_artist
        binding.fav.setImageResource(if(song.fav==1) R.drawable.ic_fav else R.drawable.ic_no_fav)
        Picasso.with(activity).load("http://spotify.rottinghex.com/Img/"+song.sgn_img)
            .placeholder(R.drawable.background)
            .into(binding.img)
        binding.playerSeekBar.max= viewModel.musicManager.getActualSongTime()!!
        binding.txtCurrentTime.text = "00:00"
        binding.txtTotalDuration.text = toMinSec(viewModel.musicManager.getActualSongTime())

    }

    private fun toMinSec(milliseconds: Int?):String{
        if (milliseconds!=null) {
            val minutes = (milliseconds / 1000 / 60)
            val seconds = (milliseconds / 1000 % 60)
            val sMinutes = if (minutes<10) "0$minutes" else "$minutes"
            val sSeconds = if (seconds<10) "0$seconds" else "$seconds"

            return "$sMinutes:$sSeconds"
        }
        return  ""
    }

    //TODO Set More Observers to Observer Data from MediaPlayer

    override fun onStart() {
        super.onStart()
        startService()
        setObservers()
    }

    fun updateCurrent(pos: Int){
        binding.txtCurrentTime.text = toMinSec(pos)
        binding.playerSeekBar.progress = pos
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


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            setTime(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

}