package com.example.kotlinmusicapp.ui.loader

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.components.PlayerService
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.SongPlayerRepository
import com.example.kotlinmusicapp.data.repository.UploadRepository
import com.example.kotlinmusicapp.data.responses.LoginResponse
import com.example.kotlinmusicapp.data.responses.RegisterResponse
import com.example.kotlinmusicapp.data.responses.types.Song
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel (
    private val repository: UploadRepository
) : ViewModel() {

    private val _updateResponse : MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val updateResponse: LiveData<Resource<RegisterResponse>> get() = _updateResponse

    fun uploadImage(img: MultipartBody.Part,song: MultipartBody.Part, name: RequestBody) {

        viewModelScope.launch {
            //Saves login response to MutableLiveData var
            _updateResponse.value = Resource.Loading
            _updateResponse.value = repository.upload(img,song,name)
        }
    }

}