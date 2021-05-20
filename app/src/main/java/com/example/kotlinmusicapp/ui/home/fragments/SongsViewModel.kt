package com.example.kotlinmusicapp.ui.home.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.SongsRepository
import com.example.kotlinmusicapp.data.responses.SongListResponse
import com.example.kotlinmusicapp.data.responses.types.Song
import kotlinx.coroutines.launch

class SongsViewModel (
    private val repository: SongsRepository
) : ViewModel() {

    private val _songListResponse : MutableLiveData<Resource<SongListResponse>> = MutableLiveData()
    val songListResponse : LiveData<Resource<SongListResponse>> get() = _songListResponse
    var list : List<Song> = emptyList();


    fun getSongList(){
        viewModelScope.launch {
            _songListResponse.value = Resource.Loading
            _songListResponse.value = repository.getSongList()
        }
    }

}