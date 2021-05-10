package com.example.kotlinmusicapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.data.responses.LoginResponse
import com.example.kotlinmusicapp.data.responses.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Response

//AuthViewModel
class AuthViewModel(
        //Needs Repository Instance
        private val repository: AuthRepository
        //Extends the AndroidStudio Base ViewModel Class
        ) : ViewModel() {
}