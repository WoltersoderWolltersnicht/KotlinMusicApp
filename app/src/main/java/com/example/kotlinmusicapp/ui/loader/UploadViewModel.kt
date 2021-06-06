package com.example.kotlinmusicapp.ui.loader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.UploadRepository
import com.example.kotlinmusicapp.data.responses.RegisterResponse
import com.example.kotlinmusicapp.data.responses.UploadResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel (
    private val repository: UploadRepository
) : ViewModel() {

    private val _updateResponse : MutableLiveData<Resource<UploadResponse>> = MutableLiveData()
    val updateResponse: LiveData<Resource<UploadResponse>> get() = _updateResponse

    private val _updateImageResponse : MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val updateImageResponse: LiveData<Resource<RegisterResponse>> get() = _updateImageResponse


    fun upload( song: MultipartBody.Part?, name: RequestBody,auth: RequestBody,gender: RequestBody,privacy: RequestBody,userId: RequestBody) {

        viewModelScope.launch {
            //Saves login response to MutableLiveData var
            _updateResponse.value = Resource.Loading
            _updateResponse.value = song?.let { repository.upload( song,name,auth,gender,privacy,userId) }
        }
    }

    fun uploadImage( image: MultipartBody.Part?, songId: RequestBody) {

        viewModelScope.launch {
            //Saves login response to MutableLiveData var
            _updateImageResponse.value = Resource.Loading
            _updateImageResponse.value = image?.let { repository.uploadImage( image,songId) }
        }
    }

    fun validSongName(name : TextInputLayout) : Boolean{

        if (name.editText?.text.toString().trim().isNullOrBlank()){
            name.error = "Name cant be empty"
            return false
        }

        name.error = null
        return true

    }

    fun validAuthor(auth : TextInputLayout) : Boolean{

        if (auth.editText?.text.toString().trim().isNullOrBlank()){
            auth.error = "Author cant be empty"
            return false
        }

        auth.error = null
        return true

    }

    fun validGender(gender : TextInputLayout) : Boolean{

        if (gender.editText?.text.toString().trim().isNullOrBlank()){
            gender.error = "Gneder cant be empty"
            return false
        }

        gender.error = null
        return true

    }

}