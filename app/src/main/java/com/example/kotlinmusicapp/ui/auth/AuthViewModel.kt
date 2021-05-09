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


        //LiveDatas for Login Responses
        private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
        val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

        //Login Method
        fun login(
                //Needs Email,Password
                email: String,
                password: String,

                //Cooroutine
        ) = viewModelScope.launch {
                //Saves login response to MutableLiveData var
                _loginResponse.value = Resource.Loading
                _loginResponse.value = repository.login(email, password)
        }

        //LiveDatas for Register Responses
        private val _registerResponse : MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
        val registerResponse: LiveData<Resource<RegisterResponse>> get() = _registerResponse

        //Register Method
        fun register(//Needs name, email, password
                     name: String,
                     email: String,
                     password: String
        //Cooroutine
        ) = viewModelScope.launch {
                //Saves login response to MutableLiveData var
                _registerResponse.value = Resource.Loading
                _registerResponse.value = repository.register(name,email, password)
        }

}