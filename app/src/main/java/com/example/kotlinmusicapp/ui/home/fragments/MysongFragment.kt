package com.example.kotlinmusicapp.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinmusicapp.MainActivity
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.MySongsApi
import com.example.kotlinmusicapp.data.network.apis.SongsApi
import com.example.kotlinmusicapp.data.repository.MysongRepository
import com.example.kotlinmusicapp.databinding.FragmentMysongBinding
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.changeFragment
import com.example.kotlinmusicapp.ui.handleApiError
import com.example.kotlinmusicapp.ui.home.HomeFragmentDirections
import com.example.kotlinmusicapp.ui.home.components.SongsRecycleViewAdapter

class MysongFragment : BaseFragment<MysongViewModel, FragmentMysongBinding, MysongRepository>(),SongsRecycleViewAdapter.OnItemClickListener {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.songListResponse.observe(viewLifecycleOwner,{
            when (it) {
                is Resource.Success -> {

                    viewModel.list = it.value.songs
                    binding.recyclerView.adapter = SongsRecycleViewAdapter(it.value.songs, this)
                    binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.recyclerView.setHasFixedSize(true)

                }
                is Resource.Failure -> handleApiError(it)
            }
        })

        viewModel.getSongList()

    }

    override fun onItemClick(position: Int) {

        val action = HomeFragmentDirections.actionHomeFragmentToSongPlayerFragment3(viewModel.list.toTypedArray(),position)
        activity?.changeFragment(MainActivity::class.java,action)
    }

    override fun getViewModel() = MysongViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) : FragmentMysongBinding = FragmentMysongBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()  = MysongRepository(remoteDataSource.buildApi(MySongsApi::class.java))

}