package com.example.kotlinmusicapp.ui.auth.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.data.responses.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel (
    private val repository: RegisterRepository
        ){

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