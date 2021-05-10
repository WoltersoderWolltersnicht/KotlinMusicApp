package com.example.kotlinmusicapp.ui.auth.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.LoginRepository
import com.example.kotlinmusicapp.data.responses.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repository: LoginRepository
    ):ViewModel(){

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
}