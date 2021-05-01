package com.example.kotlinmusicapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.network.Resource
import com.example.kotlinmusicapp.repository.AuthRepository
import com.example.kotlinmusicapp.repository.BaseRepository
import com.example.kotlinmusicapp.responses.LoginResponse
import kotlinx.coroutines.launch

class AuthViewModel(
        private val repository: AuthRepository
) : ViewModel() {

        private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
        val loginResponse: LiveData<Resource<LoginResponse>>
                get() = _loginResponse
        fun login(
                email: String,
                password: String,
        ) = viewModelScope.launch {
                _loginResponse.value = repository.login(email, password);
        }

}