package com.example.kotlinmusicapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.data.responses.LoginResponse
import kotlinx.coroutines.launch
//AuthViewModel
class AuthViewModel(
        private val repository: AuthRepository
//Extends the AndroidStudio Base ViewModel Class
) : ViewModel() {
        //Mutable Data that can change to save in LiveData var
        private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
        //LiveData var to Observe changes
        val loginResponse: LiveData<Resource<LoginResponse>>
                //Gets MutableLiveData
                get() = _loginResponse

        //Login Method
        fun login(
                //Needs Email,Password
                email: String,
                password: String,

                //Creates a coroutine
                ) = viewModelScope.launch {
                //Saves login response to MutableLiveData var
                _loginResponse.value = repository.login(email, password)
        }

        //Saves AuthToken
        fun saveAuthToken(token: String) = viewModelScope.launch {
                repository.saveAuthToken(token)
        }

}